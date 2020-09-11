package ps.zhifa.test.eltest.el.elengine.fastenginesate;

import ps.zhifa.test.eltest.el.elengine.FastElEngine;
import ps.zhifa.test.eltest.el.elengine.IStateContext;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expresselement.*;
import ps.zhifa.test.eltest.el.elengine.fastenginesate.expressionstates.*;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author 织法酱
 * 先乘除，后加减，有括号先算括号里的，先算数值后算字符串，有字符串最终变量则为字符串
 */
public class ExpressionState extends StateWithTestCharArr implements IStateContext
{
    final char[] varBegStr = "${".toCharArray();
    final char[] expressionEndStr = ")]".toCharArray();

    EExpressionState meExpressionState;
    BaseExpressionState mCurExpressionState;
    BaseExpressionState[] expressionStates;
    StringBuilder mSb;
    private int mBracket;
    protected boolean mCalExpression;
    private boolean mStringModel;
    private boolean mIntegerModel;
    private int mPriority;

    protected List<BaseExpressElement> mElements;

    protected Stack<NumberExpressElement> mValSt;
    protected Stack<OpExpressElement> mOpSt;


    public ExpressionState(FastElEngine pContext)
    {
        super(pContext, EFastElEngineState.Expression,2);
        mElements = new ArrayList<>();
        expressionStates = new BaseExpressionState[5];
        expressionStates[EExpressionState.Begin.ordinal()] = new ExpressionBeginState(this);
        expressionStates[EExpressionState.Number.ordinal()] = new ExpressionNumberState(this);
        expressionStates[EExpressionState.Sig.ordinal()] = new ExpressionSigState(this);
        expressionStates[EExpressionState.VarFinish.ordinal()] = new ExpressionVarFinishState(this);
        expressionStates[EExpressionState.String.ordinal()] = new ExpressionStringState(this);
        mValSt = new Stack<NumberExpressElement>();
        mOpSt = new Stack<OpExpressElement>();
        mCalExpression = false;
        mSb = new StringBuilder();
    }

    @Override
    public void init()
    {
        super.init();
        mElements.clear();
        if(mCalExpression)
        {
            meExpressionState = EExpressionState.Begin;
            expressionStates[EExpressionState.Begin.ordinal()].init();
            //expressionStates[EExpressionState.Number.ordinal()].init();
            //expressionStates[EExpressionState.Sig.ordinal()].init();
            //expressionStates[EExpressionState.VarFinish.ordinal()].init();
            //expressionStates[EExpressionState.String.ordinal()].init();
            mCurExpressionState = expressionStates[EExpressionState.Begin.ordinal()];
            mBracket = 1;
            mOpSt.clear();
            mValSt.clear();
        }
        else
        {
            mSb.setLength(0);
        }
        mStringModel = false;
        mIntegerModel = true;
        mPriority = -1;
    }

    public boolean getCalExpression()
    {
        return mCalExpression;
    }

    public void setCalExpression(boolean calExpression)
    {
        mCalExpression = calExpression;
    }



    @Override
    public void readOne(char c) throws Exception {
        if(' '==c||'\n'==c)
        {
            return;
        }

        readTestC(c);
        if(strCmp(mTestStr,varBegStr))
        {
            if(!mCalExpression)
            {
                mSb.delete(mSb.length()-1,mSb.length());
            }
            else
            {

                endState();
            }
            changeState(EFastElEngineState.Var.ordinal(),"");
            return;
        }
        if(strCmp(mTestStr,expressionEndStr))
        {
            if(!mCalExpression) {
                mSb.delete(mSb.length()-1,mSb.length());
            }
            else
            {

                endState();
            }
            changeState(EFastElEngineState.ReadState.ordinal(),  getResult());
            return;
        }

        if(mCalExpression)
        {
            if('$' != c)
            {
                mCurExpressionState.readC(c);
            }
        }
        else
        {
            mSb.append(c);
        }


    }

    private void endState()
    {
        BaseExpressElement result = mCurExpressionState.getResult();
        if(null != result)
        {
            mElements.add(result);
        }
    }

    @Override
    public Object getResult() throws Exception {
        if(!mCalExpression)
        {
            BaseExpressElement rtn = mElements.get(0);
            return mSb.toString() + rtn.getData();
        }
        else
        {
            return calExpression();
        }



    }

    @Override
    public void changeState(int pEState, Object pMsg) throws Exception {
        mContext.contextChangeState(this,pEState,pMsg);
    }

    public String calExpression() throws Exception {
        if(mStringModel)
        {
            return calStringExpression();
        }
        else
        {
            return calNumberExpression();
        }
    }


    public String calStringExpression() throws ServerException {
        /*
        * 算法概述，把列表中元素转为字符串相加，符号全部忽略
        * */
        StringBuilder sb = new StringBuilder();
        int elementSize = mElements.size();
        for(int i=0;i<elementSize;i++)
        {
            BaseExpressElement curEle = mElements.get(i);
            if(curEle instanceof BaseValueExpressElement)
            {
                sb.append(((BaseValueExpressElement)curEle).getValue().toString());
            }
        }
        return "\""+sb.toString()+"\"";
    }




    public String calNumberExpression() throws Exception {
        /*
        算法概述：
        设置两个栈，一个是数字栈，一个是运算符栈
        记录数据：当前运算符优先级r
        1. 遇到数字则入栈
        2. 遇到运算符，判断如果该运算符优先级大于r，则出栈2个数字，出栈1个运算符，计算后放入栈中
        3. 遇到)，则一直出栈直到遇到(
        * */
        int elementSize = mElements.size();
        for(int i=0;i<elementSize;i++)
        {
            BaseExpressElement curEle = mElements.get(i);
            if(curEle instanceof NumberExpressElement)
            {
                //System.out.println(((NumberExpressElement) curEle).getValue().toString()+" 入栈");
                mValSt.push((NumberExpressElement)curEle);
            }
            else
            {
                OpExpressElement opExp = (OpExpressElement)curEle;
                char opc = opExp.getOp();
                if(BaseExpressionState.isSig(opc))
                {
                    int curPriority = opExp.getPriority();
                    while(curPriority <= mPriority)
                    {
                        popStack();
                    }
                    mOpSt.push(opExp);
                    //System.out.println(opc+" 入栈");
                    mPriority = curPriority;
                }
                else if('(' == opc)
                {
                    mOpSt.push(opExp);
                    mPriority = 0;
                }
                else if(')' == opc)
                {
                    while (mOpSt.peek().getOp() != '(')
                    {
                        popStack();
                    }
                    mOpSt.pop();
                }
            }
        }
        while(!mOpSt.empty())
        {
            popStack();
        }
        return mValSt.pop().getValue().toString();
    }

    private void popStack() throws Exception {
        char op = mOpSt.pop().getOp();
        //System.out.println(op+" 出栈");

        if(mIntegerModel)
        {
            int iResult = 0;
            int r = ((Number)mValSt.pop().getValue()).intValue();
            int l = ((Number)mValSt.pop().getValue()).intValue();
            int res = cal(l,r,op);
            //System.out.println("cal "+l+" "+op+" "+r+" = "+res);
            mValSt.push(new NumberExpressElement(res, EExpressElementType.Integer));
        }
        else
        {
            double iResult = 0;
            double r = ((Number)mValSt.pop().getValue()).doubleValue();
            double l = ((Number)mValSt.pop().getValue()).doubleValue();
            double res = cal(l,r,op);
            //System.out.println("cal "+l+" "+op+" "+r+" = "+res);
            mValSt.push(new NumberExpressElement(res,EExpressElementType.Double));
        }
        if(!mOpSt.empty())
        {
            mPriority = mOpSt.peek().getPriority();
        }
        else
        {
            mPriority = -1;
        }
    }

    protected int cal(int pl,int pr,int pc) throws Exception {
        switch (pc)
        {
            case '+':
                return pl+pr;
            case '-':
                return pl-pr;
            case '*':
                return pl*pr;
            case '/':
                return pl/pr;
        }
        throw new Exception("发现错误符号");
    }

    protected double cal(double pl,double pr,int pc) throws Exception {
        switch (pc)
        {
            case '+':
                return pl+pr;
            case '-':
                return pl-pr;
            case '*':
                return pl*pr;
            case '/':
                return pl/pr;
        }
        throw new Exception("发现错误符号");
    }

    @Override
    public void onChangeState(Object message, int pFromState) throws Exception {
        EFastElEngineState eState = EFastElEngineState.values()[pFromState];
        switch (eState)
        {
            case ReadState:
                break;
            case Var:
                BaseExpressElement msg = (BaseExpressElement)message;
                addVarElement(msg);
                contextChangeState(mCurExpressionState,EExpressionState.VarFinish.ordinal(),msg);
                break;
        }
    }

    protected void addExpressElement(BaseExpressElement pEle)
    {
        //System.out.println("添加BaseExpressElement "+pEle.getData());
        mElements.add(pEle);
    }

    public void addVarElement(BaseExpressElement pEle)
    {
        if(pEle.getType() == EExpressElementType.String)
        {
            mStringModel = true;
        }
        else if(pEle.getType() == EExpressElementType.Double)
        {
            mIntegerModel = false;
        }
        addExpressElement(pEle);
    }

    public void addOp(char op) throws ServerException {
        addExpressElement(new OpExpressElement(op));
    }




    public void addLeftBracket()
    {
        addExpressElement(new OpExpressElement('('));
        mBracket++;
    }

    public void addRightBracket() throws Exception {
        mBracket--;
        if(mBracket<0)
        {
            throw new Exception("处理表达式时，括号不成对");
        }
        if(mBracket != 0)
        {
            addExpressElement(new OpExpressElement(')'));
        }
    }

    @Override
    public void contextChangeState(IState pFrom, int pEState, Object pMsg) throws Exception {
        meExpressionState = EExpressionState.values()[pEState];
        mCurExpressionState = expressionStates[pEState];
        mCurExpressionState.init();
        mCurExpressionState.onChangeState(pMsg,pEState);
    }
}

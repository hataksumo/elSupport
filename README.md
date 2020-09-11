# elSupport

<pre>
test data:

{
	"deviceShadow":{
		"ts" : [(${shadow.ts})],
		"temperature" : [(${shadow.temperature})],
		"potency" : [(${shadow.potency})],
		"humidity" : [(${shadow.humidity})],
		"fanState" : [(${shadow.fanState})],
		"cleanerState" : [(${shadow.cleanerState})],
		"pm" : [(${shadow.pm})],
		"opened" : [(${shadow.opened})],
		"nmch" : [(${shadow.nmch})],
		"purificationRate" : [(${shadow.purificationRate})],
		"avg10Potency" : [(${shadow.avg10Potency})],
		"businessHours" : [(${shadow.businessHours})],
		"merchantType" : [(${shadow.merchantType})]
	},
	"warning":{
		"name" : [(${warning.name})],
		"lv" : [(${warning.lv})]
	},
	"expressionTest":{
	    "purePlus" : [(2+3)],
	    "numberPlusVar1" : [(2+${shadow.ts})],
	    "numberPlusVar2" : [(${shadow.ts}+3)],
	    "multiExpress1":[(2+3*5)],
	    "multiExpress2":[(3*5+2)],
	    "multiExpress3":[(2+3-1)],
	    "multiExpress4":[(2+3-1/2.0)],
	    "multiExpress5":[(2+3-1/2.0-3)],
	    "multiExpress6":[(2+3-1/2.0-3*${shadow.ts}-${shadow.potency})],
	    "multiExpress7":[("shadow.ts_"+${shadow.ts})],
	    "multiExpress8":[(${shadow.ts}+"_\\\""+${warning.name})]
	}
}

output:

{
	"deviceShadow":{
		"ts" : 1,
		"temperature" : 35.2,
		"potency" : 0.0032,
		"humidity" : 0.0125,
		"fanState" : 1,
		"cleanerState" : 0,
		"pm" : 23.5,
		"opened" : 0,
		"nmch" : 0.0036,
		"purificationRate" : 0.0058,
		"avg10Potency" : 0.0137,
		"businessHours" : 67,
		"merchantType" : 2
	},
	"warning":{
		"name" : "smockWarning",
		"lv" : 2
	},
	"expressionTest":{
	    "purePlus" : 5,
	    "numberPlusVar1" : 3,
	    "numberPlusVar2" : 4,
	    "multiExpress1":17,
	    "multiExpress2":17,
	    "multiExpress3":4,
	    "multiExpress4":4.5,
	    "multiExpress5":1.5,
	    "multiExpress6":1.4968,
	    "multiExpress7":"shadow.ts_1",
	    "multiExpress8":"1_\"smockWarning"
	}
}

speed：

运行100次 cost 44ms
运行1000次 cost 117ms
运行10000次 cost 586ms
运行100000次 cost 3358ms
manually Replace，运行100次 cost 38ms
manually Replace，运行1000次 cost 43ms
manually Replace，运行10000次 cost 191ms
manually Replace，运行100000次 cost 1866ms

</pre>

par(las=1);

Baseline.dataframe = data.frame(
insert_student=c(0.331544433, 0.076171081, 0.089830014, 0.08071204400000001, 0.06760789, 0.065806284, 0.07272745, 0.062318506, 0.067559303, 0.087432546, 0.061996059000000006, 0.061917634000000006, 0.062954824, 0.068324735, 0.065596679),
insert_course=c(0.217760206, 0.080143488, 0.07273167600000001, 0.08011626000000001, 0.063428635, 0.066146895, 0.064139481, 0.06523081800000001, 0.06888129500000001, 0.06288371400000001, 0.06934461800000001, 0.065132039, 0.064470206, 0.068162026, 0.09734192500000001),
insert_enrolment=c(1.204973631, 0.679557417, 0.6540449700000001, 0.576354502, 0.614565145, 0.576159465, 0.572178062, 0.597554476, 0.594040417, 0.572976832, 0.597827399, 0.582100962, 0.5803266300000001, 0.5984065470000001, 0.569878685),
update_student=c(0.175741004, 0.144882334, 0.150382767, 0.13281520800000002, 0.141654537, 0.13348068200000002, 0.135955233, 0.14976008500000001, 0.13032015800000002, 0.132699873, 0.12615237, 0.132587957, 0.13698284000000002, 0.132275103, 0.19009415100000002),
update_course=c(0.169576095, 0.152471187, 0.15110669300000001, 0.128751778, 0.150361204, 0.133492953, 0.14073697300000002, 0.13043602, 0.14679560900000002, 0.13027974, 0.145332119, 0.129525796, 0.139566338, 0.14575523, 0.14662697200000002),
update_enrolment=c(0.987076955, 1.368918166, 0.8565757690000001, 0.7565007060000001, 0.761970612, 0.7862213570000001, 1.4783720710000001, 0.7605980750000001, 0.78110307, 1.7539730910000002, 0.80162064, 0.764583326, 0.8038608930000001, 0.773209354, 0.806624073),
delete_student=c(0.06983328400000001, 0.06717988100000001, 0.061926225, 0.059813963000000005, 0.06289204500000001, 0.057968726000000005, 0.057141782, 0.056832853, 0.059654766000000005, 0.057506552, 0.055199556000000004, 0.058046775, 0.057283204000000004, 0.058792242, 0.058700531),
delete_course=c(0.065420061, 0.062403875000000004, 0.050162824, 0.056347248, 0.047699941, 0.056780267, 0.043368048, 0.056589524, 0.043442584, 0.056798871, 0.04490849, 0.058409208000000004, 0.042237534, 0.058383554000000004, 0.045227114000000006),
delete_enrolment=c(0.7106407640000001, 0.632080902, 0.564466123, 0.544524608, 0.594800211, 0.5893507680000001, 0.627458081, 0.5433467820000001, 0.538570122, 0.541970324, 0.560516056, 0.556247471, 0.54693897, 0.563411049, 0.543437398));

Solution1.dataframe = data.frame(
insert_student=c(0.24265667200000002, 0.113575593, 0.11139549200000001, 0.12000079300000001, 0.101787308, 0.09779143000000001, 0.109899304, 0.10719737800000001, 0.10954925300000001, 0.106637483, 0.109772774, 0.111940706, 0.11041529800000001, 0.11181500500000001, 0.11400094100000001),
insert_course=c(0.16103838, 0.109336263, 0.10492799100000001, 0.105586806, 0.103992784, 0.10761888800000001, 0.10426652900000001, 0.10552361900000001, 0.11729223000000001, 0.10581931800000001, 0.10485446400000001, 0.11502004900000001, 0.10424312200000001, 0.11016429300000001, 0.10529647800000001),
insert_enrolment=c(3.351824398, 2.599083759, 2.54866634, 2.515123708, 2.419716853, 2.469064814, 2.494672742, 2.536724699, 2.38497702, 2.4118020380000003, 2.87481628, 2.4476425870000003, 2.4020327320000003, 2.5407799300000002, 2.5631620330000002),
update_student=c(3.931981844, 5.137165442000001, 3.9953823770000003, 3.8125576270000003, 5.3356127, 3.9875174720000004, 3.791300998, 3.9507063350000005, 3.809882049, 3.8070764720000003, 3.8926964330000002, 3.911956827, 3.7956392880000003, 3.809623418, 3.8056794380000003),
update_course=c(0.865952197, 0.706335343, 0.687586661, 1.4351134620000001, 1.6944051390000001, 0.699907312, 1.5984162940000002, 0.6777900990000001, 0.674207214, 0.652829349, 0.6947979590000001, 0.6801009330000001, 0.679992072, 1.806159783, 0.7224009440000001),
update_enrolment=c(2.7866687640000003, 2.7499025510000004, 2.788729818, 2.6357958320000003, 2.654153269, 2.6649190910000002, 2.699368196, 2.85852909, 2.6317260900000004, 2.6750953760000002, 2.6876776600000003, 2.6913458030000004, 2.6397990570000003, 2.762926777, 2.729776229),
delete_student=c(1.563501644, 1.46806027, 1.6501465190000002, 1.452391291, 1.4705758160000002, 1.489976871, 1.4974819160000001, 3.145655677, 1.466228082, 1.5437103630000002, 1.5594864730000002, 1.4779826090000001, 1.5274228310000002, 1.4583159890000001, 1.5221081010000002),
delete_course=c(0.45967654100000005, 0.42063244200000005, 0.42625930700000003, 0.397862987, 0.46557040800000005, 0.6155519970000001, 0.40954513200000003, 0.39752120700000004, 0.433171845, 0.41750284200000004, 0.429951324, 0.40411709700000004, 0.418355615, 0.401721396, 0.40869395500000005),
delete_enrolment=c(0.807984794, 0.830549968, 0.8569260030000001, 0.8649912780000001, 0.841500482, 0.847602225, 0.89453755, 0.8662799870000001, 0.8371169150000001, 0.840875404, 0.853217042, 0.8312986750000001, 0.891772317, 0.8459442420000001, 0.8388291080000001));

Solution2.dataframe = data.frame(
insert_student=c(0.134670686, 0.124761404, 0.131568699, 0.17352867500000002, 0.136992469, 0.13194772500000002, 0.125186977, 0.149607556, 0.12664703400000002, 0.126759913, 0.125385624, 0.121542648, 0.130351996, 0.12785374500000002, 0.133349311),
insert_course=c(0.13319340600000001, 0.13254072, 0.12553398200000002, 0.135426042, 0.121394679, 0.125188126, 0.12223476600000001, 0.1238351, 0.132116952, 0.12542733, 0.124176329, 0.148255319, 0.125172155, 0.12989029400000002, 0.130396492),
insert_enrolment=c(3.796171192, 2.3989190650000003, 4.038777346, 2.548027968, 2.434047734, 2.561054698, 2.486737954, 2.384552557, 2.471623719, 2.4044169230000003, 3.2576204890000002, 2.3975962450000003, 2.44380058, 2.611749924, 2.674499071),
update_student=c(3.865911699, 3.68900992, 3.6583524200000004, 3.7953449440000004, 5.983588076, 3.748629809, 4.44501068, 3.9351562030000005, 3.630863498, 3.7233544640000003, 3.746047228, 3.694319892, 3.763225455, 4.08530137, 3.9722813200000004),
update_course=c(0.676304405, 0.6488185900000001, 0.673278801, 0.6299854650000001, 0.664274919, 0.6433594330000001, 0.630620531, 0.6541607810000001, 0.6528968500000001, 0.6353183210000001, 0.6480477990000001, 0.635269702, 0.6446012200000001, 0.654782438, 0.660387479),
update_enrolment=c(2.858031451, 2.6337084390000003, 2.543868561, 2.638749289, 2.59907844, 2.7761843500000003, 2.590970547, 2.596730227, 2.618428778, 2.65428839, 2.784319505, 2.724918622, 2.6281959780000004, 2.786565888, 2.7537171170000003),
delete_student=c(1.6679611060000001, 1.6440932220000002, 1.6351770600000002, 1.650467224, 1.63483029, 1.636625084, 1.7301892470000002, 1.663456226, 1.6401911930000002, 1.6797516540000001, 1.7704792260000002, 1.687605067, 2.5087501270000003, 1.8571612110000002, 1.834994615),
delete_course=c(0.454961202, 0.42010337200000003, 0.438074405, 0.418212819, 0.43763649000000004, 0.447434247, 0.431442906, 0.443808741, 0.45510645600000005, 0.430383579, 0.45158007000000006, 0.43373245200000005, 0.455569376, 0.44388385100000005, 0.44682749400000005),
delete_enrolment=c(1.138082866, 1.076244409, 1.09958985, 1.058577659, 1.0812010090000002, 1.142198277, 1.2029344130000001, 1.1048734900000001, 2.064012191, 1.137366286, 1.166253169, 1.125213539, 1.1192156640000002, 1.290771346, 1.2664913480000002));

Solution3.dataframe = data.frame(
insert_student=c(0.489315951, 0.43556136700000003, 0.45361063, 0.43838030400000005, 0.44129126900000004, 0.404692877, 0.395016962, 0.40820752200000004, 0.40937679400000004, 0.41512759400000004, 0.40785351000000003, 0.403262571, 0.407318685, 0.40896929600000004, 0.409731031),
insert_course=c(0.458719492, 0.43591465100000004, 0.49765534800000005, 0.424002246, 0.41981595600000005, 0.42548262800000003, 0.40045515200000004, 0.40155405600000005, 0.398943604, 0.39863673800000005, 0.40026204000000004, 0.485389707, 0.38861275, 0.406618436, 0.403893936),
insert_enrolment=c(7.686964022000001, 7.104559076, 7.1183440870000005, 7.1716915850000005, 7.074883160000001, 6.857938698000001, 6.746482881, 6.832881749, 6.790415199000001, 6.965538906000001, 6.8649377110000005, 6.791563302, 6.803317127000001, 6.826703835, 7.319511349000001),
update_student=c(9.681558193, 10.106469717000001, 9.831274041, 9.32691906, 9.70712557, 9.311565037000001, 9.114567323000001, 8.985939466000001, 8.95803193, 9.518502243, 9.085612889, 8.975268844, 8.981402266, 9.045900134, 8.936535633),
update_course=c(2.468496203, 1.330409613, 1.300654224, 1.242919115, 1.285072454, 1.218715932, 1.2332142350000002, 1.245981593, 1.275996208, 1.23591136, 1.306132797, 1.450673082, 1.2438040240000001, 1.2252170740000001, 1.241786568),
update_enrolment=c(7.628125389, 7.584613705000001, 7.609187823, 7.157335329, 7.28617173, 7.009847720000001, 7.068912232000001, 7.28428971, 7.084345344000001, 7.018946801, 7.066193314, 7.0181253240000006, 7.075505765000001, 7.100186414, 7.059670846),
delete_student=c(5.306649565000001, 5.203750477000001, 5.803679408000001, 5.213826128, 4.953435939, 5.003347488, 5.100623846, 5.184310905, 4.993880923000001, 5.050563203, 5.114972118000001, 5.169868068, 5.0938604100000004, 5.085288263000001, 5.03618028),
delete_course=c(0.815988103, 0.7705211750000001, 0.796370792, 0.759558505, 0.7880704550000001, 0.8161939340000001, 0.739537807, 0.7476240980000001, 0.7734496150000001, 0.741801934, 0.748888772, 0.769336961, 0.7496037470000001, 0.752969636, 0.785974413),
delete_enrolment=c(4.458108871, 4.331998678000001, 4.507931105, 4.380977089, 4.344562326, 4.827841531000001, 4.178509341, 4.1360006700000005, 4.949640535, 4.1726111470000005, 4.1448600650000005, 4.133825646, 4.127039768, 4.172445657, 4.158823653000001));

Solution4.dataframe = data.frame(
insert_student=c(0.067357234, 0.0649561, 0.059566992000000006, 0.060718992000000006, 0.060168754000000005, 0.059699978, 0.061573482000000006, 0.06096317800000001, 0.062724957, 0.05943466100000001, 0.06427284200000001, 0.060089479, 0.063878689, 0.06327252600000001, 0.063484383),
insert_course=c(0.060500135000000003, 0.059746812, 0.06485922100000001, 0.06930665700000001, 0.060413181, 0.060022865, 0.059611307, 0.057184422000000006, 0.058178771000000004, 0.05720381, 0.058522038000000005, 0.07213486300000001, 0.056003868000000005, 0.062236754000000005, 0.059881458000000005),
insert_enrolment=c(1.814815344, 2.029160178, 1.7199742850000002, 1.724070484, 1.8030383090000002, 1.726161384, 1.7087186820000002, 1.7705337760000002, 1.685228452, 1.698839283, 1.7525533580000001, 1.7819159610000002, 1.750906655, 1.7703987170000002, 1.8546044320000001),
update_student=c(2.761233544, 2.764380945, 2.7804765230000004, 2.754973301, 2.753297352, 2.7357210910000003, 2.722267255, 2.795370339, 2.8108607880000003, 2.9119985280000003, 2.80300102, 3.043698918, 2.764736796, 2.8658407670000003, 2.7898281160000002),
update_course=c(0.488387069, 0.48780339300000003, 0.518228594, 0.518962763, 0.510932226, 0.488688799, 0.511692021, 0.48909691600000005, 0.5000359990000001, 0.494840628, 0.48325841, 0.49540540600000005, 0.551319753, 0.547453186, 0.565303585),
update_enrolment=c(1.993448228, 1.9865403940000002, 1.9485974300000002, 2.3256218210000004, 1.9493075890000002, 1.9243601810000002, 1.918765797, 1.8976414590000001, 1.874604152, 1.9479283440000001, 1.9063608850000002, 1.905472379, 1.9325459870000001, 1.9787165130000002, 1.9642180480000002),
delete_student=c(1.0021426340000001, 0.9676375810000001, 0.971005624, 0.9851870840000001, 1.586453377, 1.046819243, 1.027208422, 0.9921892910000001, 1.1443586920000002, 0.988552151, 0.993103732, 1.053364341, 1.001705062, 1.0119609920000001, 0.9906490960000001),
delete_course=c(0.349805625, 0.348021974, 0.339305105, 0.334574608, 0.408748586, 0.35774778700000004, 0.34777066, 0.36808455300000004, 0.351220032, 0.339809289, 0.342624017, 0.33698050900000004, 0.35158208300000005, 0.341569824, 0.357842608),
delete_enrolment=c(0.542523191, 0.514451895, 0.522435266, 0.5049733750000001, 0.5147704670000001, 0.5150790980000001, 0.561298317, 0.520666908, 0.5148882450000001, 0.54790475, 0.509203647, 0.5190692600000001, 0.5558837710000001, 1.1719981700000002, 0.520199407));

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-insert_student-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$insert_student,Solution1.dataframe$insert_student,Solution2.dataframe$insert_student,Solution3.dataframe$insert_student,Solution4.dataframe$insert_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-insert_student-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$insert_student,1000 / Solution1.dataframe$insert_student,1000 / Solution2.dataframe$insert_student,1000 / Solution3.dataframe$insert_student,1000 / Solution4.dataframe$insert_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-insert_course-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$insert_course,Solution1.dataframe$insert_course,Solution2.dataframe$insert_course,Solution3.dataframe$insert_course,Solution4.dataframe$insert_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-insert_course-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$insert_course,1000 / Solution1.dataframe$insert_course,1000 / Solution2.dataframe$insert_course,1000 / Solution3.dataframe$insert_course,1000 / Solution4.dataframe$insert_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-insert_enrolment-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$insert_enrolment,Solution1.dataframe$insert_enrolment,Solution2.dataframe$insert_enrolment,Solution3.dataframe$insert_enrolment,Solution4.dataframe$insert_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-insert_enrolment-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(10000 / Baseline.dataframe$insert_enrolment,10000 / Solution1.dataframe$insert_enrolment,10000 / Solution2.dataframe$insert_enrolment,10000 / Solution3.dataframe$insert_enrolment,10000 / Solution4.dataframe$insert_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-update_student-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$update_student,Solution1.dataframe$update_student,Solution2.dataframe$update_student,Solution3.dataframe$update_student,Solution4.dataframe$update_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-update_student-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$update_student,1000 / Solution1.dataframe$update_student,1000 / Solution2.dataframe$update_student,1000 / Solution3.dataframe$update_student,1000 / Solution4.dataframe$update_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-update_course-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$update_course,Solution1.dataframe$update_course,Solution2.dataframe$update_course,Solution3.dataframe$update_course,Solution4.dataframe$update_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-update_course-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$update_course,1000 / Solution1.dataframe$update_course,1000 / Solution2.dataframe$update_course,1000 / Solution3.dataframe$update_course,1000 / Solution4.dataframe$update_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-update_enrolment-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$update_enrolment,Solution1.dataframe$update_enrolment,Solution2.dataframe$update_enrolment,Solution3.dataframe$update_enrolment,Solution4.dataframe$update_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-update_enrolment-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(10000 / Baseline.dataframe$update_enrolment,10000 / Solution1.dataframe$update_enrolment,10000 / Solution2.dataframe$update_enrolment,10000 / Solution3.dataframe$update_enrolment,10000 / Solution4.dataframe$update_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-delete_student-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$delete_student,Solution1.dataframe$delete_student,Solution2.dataframe$delete_student,Solution3.dataframe$delete_student,Solution4.dataframe$delete_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-delete_student-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$delete_student,1000 / Solution1.dataframe$delete_student,1000 / Solution2.dataframe$delete_student,1000 / Solution3.dataframe$delete_student,1000 / Solution4.dataframe$delete_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-delete_course-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$delete_course,Solution1.dataframe$delete_course,Solution2.dataframe$delete_course,Solution3.dataframe$delete_course,Solution4.dataframe$delete_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-delete_course-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$delete_course,1000 / Solution1.dataframe$delete_course,1000 / Solution2.dataframe$delete_course,1000 / Solution3.dataframe$delete_course,1000 / Solution4.dataframe$delete_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-delete_enrolment-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$delete_enrolment,Solution1.dataframe$delete_enrolment,Solution2.dataframe$delete_enrolment,Solution3.dataframe$delete_enrolment,Solution4.dataframe$delete_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/boxplot-delete_enrolment-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(10000 / Baseline.dataframe$delete_enrolment,10000 / Solution1.dataframe$delete_enrolment,10000 / Solution2.dataframe$delete_enrolment,10000 / Solution3.dataframe$delete_enrolment,10000 / Solution4.dataframe$delete_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
def.barplot.width=1; #bar width
def.barplot.sepwidth=0; #separation bar width
def.barplot.space=0; #space between bars
def.barplot.groupspace=1; #space between groups
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-Baseline.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Baseline.barplot.means=data.frame(insert=c(mean(Baseline.dataframe$insert_student), mean(Baseline.dataframe$insert_course), mean(Baseline.dataframe$insert_enrolment)),
update=c(mean(Baseline.dataframe$update_student), mean(Baseline.dataframe$update_course), mean(Baseline.dataframe$update_enrolment)),
delete=c(mean(Baseline.dataframe$delete_student), mean(Baseline.dataframe$delete_course), mean(Baseline.dataframe$delete_enrolment)));
barplot(as.matrix(Baseline.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-Solution1.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Solution1.barplot.means=data.frame(insert=c(mean(Solution1.dataframe$insert_student), mean(Solution1.dataframe$insert_course), mean(Solution1.dataframe$insert_enrolment)),
update=c(mean(Solution1.dataframe$update_student), mean(Solution1.dataframe$update_course), mean(Solution1.dataframe$update_enrolment)),
delete=c(mean(Solution1.dataframe$delete_student), mean(Solution1.dataframe$delete_course), mean(Solution1.dataframe$delete_enrolment)));
barplot(as.matrix(Solution1.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-Solution2.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Solution2.barplot.means=data.frame(insert=c(mean(Solution2.dataframe$insert_student), mean(Solution2.dataframe$insert_course), mean(Solution2.dataframe$insert_enrolment)),
update=c(mean(Solution2.dataframe$update_student), mean(Solution2.dataframe$update_course), mean(Solution2.dataframe$update_enrolment)),
delete=c(mean(Solution2.dataframe$delete_student), mean(Solution2.dataframe$delete_course), mean(Solution2.dataframe$delete_enrolment)));
barplot(as.matrix(Solution2.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-Solution3.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Solution3.barplot.means=data.frame(insert=c(mean(Solution3.dataframe$insert_student), mean(Solution3.dataframe$insert_course), mean(Solution3.dataframe$insert_enrolment)),
update=c(mean(Solution3.dataframe$update_student), mean(Solution3.dataframe$update_course), mean(Solution3.dataframe$update_enrolment)),
delete=c(mean(Solution3.dataframe$delete_student), mean(Solution3.dataframe$delete_course), mean(Solution3.dataframe$delete_enrolment)));
barplot(as.matrix(Solution3.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-Solution4.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Solution4.barplot.means=data.frame(insert=c(mean(Solution4.dataframe$insert_student), mean(Solution4.dataframe$insert_course), mean(Solution4.dataframe$insert_enrolment)),
update=c(mean(Solution4.dataframe$update_student), mean(Solution4.dataframe$update_course), mean(Solution4.dataframe$update_enrolment)),
delete=c(mean(Solution4.dataframe$delete_student), mean(Solution4.dataframe$delete_course), mean(Solution4.dataframe$delete_enrolment)));
barplot(as.matrix(Solution4.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-insert-rt.pdf' );
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

insert.barplot.means=data.frame(Baseline = c(mean(Baseline.dataframe$insert_student), mean(Baseline.dataframe$insert_course), mean(Baseline.dataframe$insert_enrolment)), Solution1 = c(mean(Solution1.dataframe$insert_student), mean(Solution1.dataframe$insert_course), mean(Solution1.dataframe$insert_enrolment)), Solution2 = c(mean(Solution2.dataframe$insert_student), mean(Solution2.dataframe$insert_course), mean(Solution2.dataframe$insert_enrolment)), Solution3 = c(mean(Solution3.dataframe$insert_student), mean(Solution3.dataframe$insert_course), mean(Solution3.dataframe$insert_enrolment)), Solution4 = c(mean(Solution4.dataframe$insert_student), mean(Solution4.dataframe$insert_course), mean(Solution4.dataframe$insert_enrolment)));
barplot(as.matrix(insert.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-insert-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

insert.barplot.means=data.frame(Baseline = c(1000 / mean(Baseline.dataframe$insert_student), 1000 / mean(Baseline.dataframe$insert_course), 10000 / mean(Baseline.dataframe$insert_enrolment)), Solution1 = c(1000 / mean(Solution1.dataframe$insert_student), 1000 / mean(Solution1.dataframe$insert_course), 10000 / mean(Solution1.dataframe$insert_enrolment)), Solution2 = c(1000 / mean(Solution2.dataframe$insert_student), 1000 / mean(Solution2.dataframe$insert_course), 10000 / mean(Solution2.dataframe$insert_enrolment)), Solution3 = c(1000 / mean(Solution3.dataframe$insert_student), 1000 / mean(Solution3.dataframe$insert_course), 10000 / mean(Solution3.dataframe$insert_enrolment)), Solution4 = c(1000 / mean(Solution4.dataframe$insert_student), 1000 / mean(Solution4.dataframe$insert_course), 10000 / mean(Solution4.dataframe$insert_enrolment)));
barplot(as.matrix(insert.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Entities per second', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-update-rt.pdf' );
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

update.barplot.means=data.frame(Baseline = c(mean(Baseline.dataframe$update_student), mean(Baseline.dataframe$update_course), mean(Baseline.dataframe$update_enrolment)), Solution1 = c(mean(Solution1.dataframe$update_student), mean(Solution1.dataframe$update_course), mean(Solution1.dataframe$update_enrolment)), Solution2 = c(mean(Solution2.dataframe$update_student), mean(Solution2.dataframe$update_course), mean(Solution2.dataframe$update_enrolment)), Solution3 = c(mean(Solution3.dataframe$update_student), mean(Solution3.dataframe$update_course), mean(Solution3.dataframe$update_enrolment)), Solution4 = c(mean(Solution4.dataframe$update_student), mean(Solution4.dataframe$update_course), mean(Solution4.dataframe$update_enrolment)));
barplot(as.matrix(update.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-update-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

update.barplot.means=data.frame(Baseline = c(1000 / mean(Baseline.dataframe$update_student), 1000 / mean(Baseline.dataframe$update_course), 10000 / mean(Baseline.dataframe$update_enrolment)), Solution1 = c(1000 / mean(Solution1.dataframe$update_student), 1000 / mean(Solution1.dataframe$update_course), 10000 / mean(Solution1.dataframe$update_enrolment)), Solution2 = c(1000 / mean(Solution2.dataframe$update_student), 1000 / mean(Solution2.dataframe$update_course), 10000 / mean(Solution2.dataframe$update_enrolment)), Solution3 = c(1000 / mean(Solution3.dataframe$update_student), 1000 / mean(Solution3.dataframe$update_course), 10000 / mean(Solution3.dataframe$update_enrolment)), Solution4 = c(1000 / mean(Solution4.dataframe$update_student), 1000 / mean(Solution4.dataframe$update_course), 10000 / mean(Solution4.dataframe$update_enrolment)));
barplot(as.matrix(update.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Entities per second', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-delete-rt.pdf' );
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

delete.barplot.means=data.frame(Baseline = c(mean(Baseline.dataframe$delete_student), mean(Baseline.dataframe$delete_course), mean(Baseline.dataframe$delete_enrolment)), Solution1 = c(mean(Solution1.dataframe$delete_student), mean(Solution1.dataframe$delete_course), mean(Solution1.dataframe$delete_enrolment)), Solution2 = c(mean(Solution2.dataframe$delete_student), mean(Solution2.dataframe$delete_course), mean(Solution2.dataframe$delete_enrolment)), Solution3 = c(mean(Solution3.dataframe$delete_student), mean(Solution3.dataframe$delete_course), mean(Solution3.dataframe$delete_enrolment)), Solution4 = c(mean(Solution4.dataframe$delete_student), mean(Solution4.dataframe$delete_course), mean(Solution4.dataframe$delete_enrolment)));
barplot(as.matrix(delete.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-delete-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

delete.barplot.means=data.frame(Baseline = c(1000 / mean(Baseline.dataframe$delete_student), 1000 / mean(Baseline.dataframe$delete_course), 10000 / mean(Baseline.dataframe$delete_enrolment)), Solution1 = c(1000 / mean(Solution1.dataframe$delete_student), 1000 / mean(Solution1.dataframe$delete_course), 10000 / mean(Solution1.dataframe$delete_enrolment)), Solution2 = c(1000 / mean(Solution2.dataframe$delete_student), 1000 / mean(Solution2.dataframe$delete_course), 10000 / mean(Solution2.dataframe$delete_enrolment)), Solution3 = c(1000 / mean(Solution3.dataframe$delete_student), 1000 / mean(Solution3.dataframe$delete_course), 10000 / mean(Solution3.dataframe$delete_enrolment)), Solution4 = c(1000 / mean(Solution4.dataframe$delete_student), 1000 / mean(Solution4.dataframe$delete_course), 10000 / mean(Solution4.dataframe$delete_enrolment)));
barplot(as.matrix(delete.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Entities per second', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();
pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-insert_student-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.08816663213333334, 0.118562362, 0.13334363079999997, 0.42184775753333337, 0.06214414980000001), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-insert_student-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(11342.159451976253, 8434.379875124283, 7499.420812231252, 2370.523446295628, 16091.619295111826), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-insert_course-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.0803942188, 0.1109987476, 0.12898544613333335, 0.4230637826666667, 0.06105374413333333), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-insert_course-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(12438.705356261264, 9009.11065774944, 7752.812662029261, 2363.7097784565103, 16379.01187216515), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-insert_enrolment-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.6380630093333334, 2.5706726622000002, 2.7273063643333333, 6.9970488458, 1.7727279533333336), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-insert_enrolment-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(15672.433370566785, 3890.032421102548, 3666.6214440651643, 1429.1739589616457, 5641.0234752583365), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-update_student-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.14305228679999998, 4.0516519146666665, 3.9824264652000005, 9.304444823066667, 2.8038456855333336), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-update_student-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(6990.450990819114, 246.8129101564913, 251.1031926737106, 107.47551509155045, 356.6530088155636), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-update_course-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.1427209804666667, 0.9517329840666666, 0.6501404489333334, 1.3536656321333331, 0.5100939165333334), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-update_course-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(7006.678322487812, 1050.714871441245, 1538.129186763677, 738.7348664707048, 1960.4233016463597), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-update_enrolment-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.9494138772, 2.710427573533333, 2.679183705466667, 7.203430496400001, 1.9636086138000006), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-update_enrolment-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(10532.814234285135, 3689.454792169166, 3732.480150426331, 1388.2274570425323, 5092.664561420858), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-delete_student-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.059918159, 1.6195362968, 1.7494488367999999, 5.154282468066667, 1.0508224881333337), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-delete_student-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(16689.431329156825, 617.4606904308808, 571.6085997857174, 194.01342596093548, 951.6355153156134), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-delete_course-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.052545276200000005, 0.4337422730000001, 0.4405838306666667, 0.7703926631333334, 0.3517124840000001), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-delete_course-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(19031.20646266581, 2305.516575738514, 2269.7156145899776, 1298.039360775854, 2843.2314617527186), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-delete_enrolment-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.5771839752666668, 0.8499617326666669, 1.2048683677333334, 4.3350117388000005, 0.5690230511333333), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/home/jcrada/Development/hr/ResearchTestApplication/logs/sAll-r15-s100-c100-cs10/barplot-delete_enrolment-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(17325.49833071832, 11765.235557871572, 8299.661828463939, 2306.798828362148, 17573.98049179699), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();


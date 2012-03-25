par(las=1);

Baseline.dataframe = data.frame(
insert_student=c(216351.12, 50156.726, 46574.368, 42957.495, 45322.077, 44802.401, 46920.101, 46105.324, 43939.163, 43849.6, 45664.503000000004, 46413.251000000004, 49690.332, 47627.528, 45251.272000000004),
insert_course=c(121987.283, 45119.062, 44718.793, 44988.252, 43382.955, 50216.092000000004, 43852.624, 44548.124, 44363.427, 43721.216, 45284.798, 45397.07, 47437.522000000004, 48946.963, 47058.968),
insert_enrolment=c(730953.209, 463259.059, 431999.17600000004, 491897.8, 414775.38300000003, 416492.32300000003, 502421.895, 415893.593, 417699.331, 414927.742, 420221.902, 420121.874, 423121.58, 423013.39900000003, 434014.35000000003),
update_student=c(105538.009, 98154.68400000001, 94548.166, 90498.583, 93683.191, 147136.283, 95349.803, 95058.306, 94636.225, 94218.571, 95119.039, 102123.30900000001, 99869.64, 96592.144, 93597.303),
update_course=c(118830.295, 95268.424, 121432.037, 94357.138, 104054.886, 102465.446, 108898.27500000001, 96750.323, 108690.692, 92954.482, 111925.21800000001, 103487.284, 111948.693, 97238.6, 114829.63),
update_enrolment=c(650782.699, 618981.38, 572768.837, 558867.559, 575495.001, 569086.583, 646261.882, 562454.973, 561955.612, 626676.5380000001, 623654.708, 649873.283, 734609.381, 611467.969, 622697.402),
delete_student=c(44688.706, 43000.159, 49536.235, 42129.691, 42267.464, 52616.686, 43956.029, 43255.4, 43989.723, 45224.901, 47230.062, 42718.373, 46617.817, 45274.094, 42750.340000000004),
delete_course=c(37402.31, 44802.951, 34258.231, 43919.452, 33633.694, 46250.032, 33693.251000000004, 44920.053, 105038.03600000001, 44181.899, 33905.562, 46197.079, 39981.298, 45243.358, 35369.695),
delete_enrolment=c(465364.819, 479285.16000000003, 459251.246, 458268.668, 464386.599, 419931.85000000003, 437386.14900000003, 465385.83400000003, 425927.162, 419067.501, 421238.686, 492532.454, 493015.06200000003, 422678.765, 420938.005));

Solution1.dataframe = data.frame(
insert_student=c(180778.429, 72328.833, 71597.744, 70146.90000000001, 73133.28600000001, 71988.197, 73415.658, 72898.719, 74941.76, 72018.641, 74212.049, 74017.734, 77510.534, 78338.874, 77664.091),
insert_course=c(83973.534, 66756.759, 69616.517, 116682.537, 70916.206, 73054.039, 71212.845, 72654.471, 72411.095, 72345.801, 72586.113, 71848.441, 74308.102, 81949.195, 74247.029),
insert_enrolment=c(1855262.646, 1675126.671, 1655189.385, 1863179.48, 1653019.7280000001, 1671579.104, 1673110.322, 1686165.512, 1766852.76, 1953142.538, 1707463.78, 1669991.182, 1714216.757, 1748497.912, 1685620.879),
update_student=c(2796299.253, 2748399.933, 2737353.399, 2777149.543, 2760298.903, 2793606.382, 2785261.251, 2805851.532, 2820590.845, 2797074.799, 2773680.856, 2856618.7430000002, 2891240.7460000003, 2878545.6520000002, 2827747.769),
update_course=c(556826.11, 562520.995, 521506.75200000004, 503253.674, 583220.845, 570117.205, 592795.858, 501077.36600000004, 527216.461, 526368.1240000001, 622742.334, 507389.152, 531798.696, 503544.89, 542936.6460000001),
update_enrolment=c(1902729.843, 1864557.838, 1913042.661, 1895721.607, 1860282.409, 1877942.395, 2060628.431, 1937547.888, 1921159.53, 1856524.406, 1913292.171, 1978783.513, 1931171.288, 1942281.173, 1946515.475),
delete_student=c(1095783.8900000001, 1088858.577, 1095926.281, 1128810.824, 1110500.864, 1138641.8900000001, 1091605.718, 1100171.546, 1132963.993, 1081919.58, 1114871.962, 1093859.936, 1130914.466, 1140319.997, 1127687.231),
delete_course=c(341317.422, 381379.886, 365468.425, 341950.998, 326665.123, 318327.637, 396296.945, 388548.953, 335709.167, 320249.84500000003, 339883.364, 322162.131, 333285.275, 319257.358, 414000.411),
delete_enrolment=c(609901.2170000001, 686178.411, 603099.09, 608023.97, 628155.782, 713789.333, 646162.637, 618416.784, 626889.954, 591249.4720000001, 587188.241, 599622.75, 604312.8250000001, 609058.1950000001, 643613.485));

Solution2.dataframe = data.frame(
insert_student=c(98906.189, 94131.839, 94609.879, 91315.594, 95529.836, 92726.448, 94429.642, 94333.053, 94010.015, 97932.967, 96038.909, 94794.928, 95538.285, 94679.447, 95029.758),
insert_course=c(90422.878, 88506.869, 90734.75600000001, 98635.024, 93396.11200000001, 92768.479, 92600.27, 92532.99, 93724.75600000001, 96648.87700000001, 93215.101, 95009.337, 95017.414, 99823.23700000001, 96919.781),
insert_enrolment=c(1749792.394, 1716230.164, 1752142.669, 1876947.323, 1737091.546, 1734884.5960000001, 1727103.134, 1796539.783, 1902705.664, 1882716.8290000001, 1936687.707, 1956029.974, 1928625.045, 1948779.3290000001, 1920595.2210000001),
update_student=c(2771533.0640000002, 2730862.7090000003, 2708253.273, 2801543.899, 2785017.257, 2859334.4620000003, 2803429.614, 3303949.785, 2957289.612, 3010748.37, 2960883.566, 3047768.342, 3106658.095, 2980110.4790000003, 2922508.534),
update_course=c(507413.056, 505314.772, 506648.819, 493872.354, 510908.31200000003, 489433.153, 569794.011, 500506.148, 511177.382, 496846.11, 512166.224, 497899.858, 513073.368, 502164.908, 529321.454),
update_enrolment=c(1914028.982, 1926194.198, 2006038.1030000001, 1908398.899, 1925646.609, 1927953.377, 2034198.561, 1908281.053, 2061051.242, 2076894.1160000002, 2087218.3930000002, 2116826.044, 2089408.877, 2107018.674, 2056844.6160000002),
delete_student=c(1326234.583, 1356749.433, 1326731.939, 1337398.508, 1344412.4340000001, 1388236.661, 1332331.144, 1475384.712, 1506346.797, 1522082.1130000001, 1493125.023, 1518301.143, 1484248.85, 1528772.163, 1502807.7310000001),
delete_course=c(364384.068, 369187.805, 357246.234, 358507.79, 374317.529, 349094.65, 362395.945, 402518.992, 449576.145, 359231.886, 366993.876, 351430.696, 399541.832, 353572.43, 360217.113),
delete_enrolment=c(841888.057, 946947.914, 870003.196, 871778.859, 928536.7540000001, 873377.039, 885667.012, 1179194.252, 1044625.582, 1026799.264, 1048687.056, 1056553.191, 1047892.81, 1130065.447, 1039373.125));

Solution3.dataframe = data.frame(
insert_student=c(361928.903, 337558.289, 334737.337, 329265.957, 335311.654, 340749.907, 338221.34500000003, 339879.907, 339690.996, 333959.732, 336071.983, 338816.767, 352337.806, 335614.04, 337312.226),
insert_course=c(357573.403, 330689.076, 341527.844, 347795.993, 333375.411, 344115.402, 341358.64400000003, 335887.184, 339787.844, 394101.996, 340701.233, 336490.522, 345625.953, 395283.92600000004, 351913.071),
insert_enrolment=c(5326884.174, 5262606.375, 5286783.38, 5333608.318, 5332718.848, 5248901.831, 5255799.073, 5262941.275, 5225874.075, 5212771.61, 5369403.858, 5292094.756, 5306413.896, 5293736.058, 5357672.506),
update_student=c(7105081.681, 7099539.399, 7139334.124, 7117188.608, 7072160.932, 7135086.549000001, 7407796.600000001, 7143057.93, 7181772.822, 7313123.835, 7113031.658, 7184363.598, 8996418.613, 7183956.122, 7177381.011),
update_course=c(1078624.212, 1070206.455, 1045994.6780000001, 1029625.2270000001, 1076980.375, 1102077.15, 1108500.7380000001, 1029927.165, 1040207.844, 1035402.04, 1087789.764, 1066566.2920000001, 1110165.803, 1068096.454, 1126122.854),
update_enrolment=c(5477544.934, 5479125.341, 5428707.024, 5441162.047, 5424600.814, 5430028.517, 5621313.78, 5497758.268, 5474023.073, 5494584.3270000005, 5452249.355, 5468533.622, 5494360.973, 5573777.751, 5512136.837),
delete_student=c(4219454.377, 4248048.939, 4208233.249, 4287295.572, 4281504.5540000005, 4315662.975, 4301540.981, 4258106.141, 4278518.932, 4314489.999, 4252110.343, 4298031.139, 4263692.367000001, 4326086.322, 4390119.149),
delete_course=c(653548.58, 682436.905, 660837.81, 631795.084, 650291.116, 631688.319, 646985.822, 727825.408, 646113.949, 640016.263, 690007.593, 650752.089, 703851.6730000001, 655431.612, 655224.728),
delete_enrolment=c(3509276.665, 3586614.031, 3526185.136, 3497389.165, 3530096.815, 3507276.398, 3518220.494, 3508428.694, 3532404.796, 3508122.7970000003, 3518237.466, 3564463.02, 3682414.436, 3508870.636, 3537214.132));

Solution4.dataframe = data.frame(
insert_student=c(88989.278, 44817.563, 44496.286, 48423.282, 45248.631, 44134.789000000004, 46039.116, 45689.516, 44385.654, 44416.243, 46658.099, 46857.47, 85624.567, 45887.053, 45628.926),
insert_course=c(43515.082, 40582.672, 42091.453, 140898.445, 43583.378000000004, 56280.225, 44257.675, 44651.295, 44932.366, 43431.049, 46080.702, 45508.891, 46278.587, 61174.802, 47132.311),
insert_enrolment=c(2178930.973, 2159234.965, 2087771.891, 2759030.999, 2092831.372, 2104357.952, 2163517.952, 2165972.354, 2103321.6210000003, 2069232.928, 2120614.179, 2167056.647, 2103737.108, 2104629.205, 2110378.703),
update_student=c(3110308.727, 3076401.421, 3051476.324, 3122650.075, 3029115.002, 3130080.384, 3080942.668, 3142778.205, 3116019.682, 3181559.8910000003, 3059016.525, 3143476.281, 3979599.434, 3179518.486, 3100144.936),
update_course=c(459841.759, 440547.178, 451632.436, 445599.71400000004, 454325.364, 438919.55, 465807.0, 442903.86600000004, 464427.001, 447794.525, 464047.047, 442360.762, 462064.788, 437514.202, 458642.015),
update_enrolment=c(2281201.759, 2213230.063, 2312810.804, 2261304.761, 2294576.449, 2239055.57, 2409028.616, 2256889.981, 2293549.5810000002, 2298693.174, 2310642.68, 2260582.9960000003, 2264517.119, 2393441.403, 2285903.836),
delete_student=c(843506.4990000001, 903575.983, 871165.843, 978053.128, 865957.475, 943477.829, 886154.14, 923041.545, 886577.976, 923104.368, 876950.216, 884408.6510000001, 890001.4450000001, 861707.627, 883077.0530000001),
delete_course=c(356207.367, 355935.898, 353683.759, 356945.9, 394907.686, 350136.327, 363403.98600000003, 360032.645, 395976.173, 347665.751, 358872.21400000004, 349371.342, 480352.882, 351546.01900000003, 362144.93700000003),
delete_enrolment=c(413874.583, 482963.357, 475802.821, 435774.319, 484311.005, 433294.412, 465434.33, 442370.309, 445821.065, 445981.82, 475048.438, 428483.905, 564814.6410000001, 420471.24, 419610.536));

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-insert_student-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$insert_student,Solution1.dataframe$insert_student,Solution2.dataframe$insert_student,Solution3.dataframe$insert_student,Solution4.dataframe$insert_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-insert_student-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$insert_student,1000 / Solution1.dataframe$insert_student,1000 / Solution2.dataframe$insert_student,1000 / Solution3.dataframe$insert_student,1000 / Solution4.dataframe$insert_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-insert_course-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$insert_course,Solution1.dataframe$insert_course,Solution2.dataframe$insert_course,Solution3.dataframe$insert_course,Solution4.dataframe$insert_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-insert_course-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$insert_course,1000 / Solution1.dataframe$insert_course,1000 / Solution2.dataframe$insert_course,1000 / Solution3.dataframe$insert_course,1000 / Solution4.dataframe$insert_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-insert_enrolment-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$insert_enrolment,Solution1.dataframe$insert_enrolment,Solution2.dataframe$insert_enrolment,Solution3.dataframe$insert_enrolment,Solution4.dataframe$insert_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-insert_enrolment-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(10000 / Baseline.dataframe$insert_enrolment,10000 / Solution1.dataframe$insert_enrolment,10000 / Solution2.dataframe$insert_enrolment,10000 / Solution3.dataframe$insert_enrolment,10000 / Solution4.dataframe$insert_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-update_student-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$update_student,Solution1.dataframe$update_student,Solution2.dataframe$update_student,Solution3.dataframe$update_student,Solution4.dataframe$update_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-update_student-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$update_student,1000 / Solution1.dataframe$update_student,1000 / Solution2.dataframe$update_student,1000 / Solution3.dataframe$update_student,1000 / Solution4.dataframe$update_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-update_course-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$update_course,Solution1.dataframe$update_course,Solution2.dataframe$update_course,Solution3.dataframe$update_course,Solution4.dataframe$update_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-update_course-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$update_course,1000 / Solution1.dataframe$update_course,1000 / Solution2.dataframe$update_course,1000 / Solution3.dataframe$update_course,1000 / Solution4.dataframe$update_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-update_enrolment-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$update_enrolment,Solution1.dataframe$update_enrolment,Solution2.dataframe$update_enrolment,Solution3.dataframe$update_enrolment,Solution4.dataframe$update_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-update_enrolment-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(10000 / Baseline.dataframe$update_enrolment,10000 / Solution1.dataframe$update_enrolment,10000 / Solution2.dataframe$update_enrolment,10000 / Solution3.dataframe$update_enrolment,10000 / Solution4.dataframe$update_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-delete_student-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$delete_student,Solution1.dataframe$delete_student,Solution2.dataframe$delete_student,Solution3.dataframe$delete_student,Solution4.dataframe$delete_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-delete_student-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$delete_student,1000 / Solution1.dataframe$delete_student,1000 / Solution2.dataframe$delete_student,1000 / Solution3.dataframe$delete_student,1000 / Solution4.dataframe$delete_student,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-delete_course-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$delete_course,Solution1.dataframe$delete_course,Solution2.dataframe$delete_course,Solution3.dataframe$delete_course,Solution4.dataframe$delete_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-delete_course-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(1000 / Baseline.dataframe$delete_course,1000 / Solution1.dataframe$delete_course,1000 / Solution2.dataframe$delete_course,1000 / Solution3.dataframe$delete_course,1000 / Solution4.dataframe$delete_course,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-delete_enrolment-rt.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(Baseline.dataframe$delete_enrolment,Solution1.dataframe$delete_enrolment,Solution2.dataframe$delete_enrolment,Solution3.dataframe$delete_enrolment,Solution4.dataframe$delete_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time in seconds', outline=F, horizontal=F);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/boxplot-delete_enrolment-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0.5), cex.axis=1.1, cex.lab=1.3);

boxplot(10000 / Baseline.dataframe$delete_enrolment,10000 / Solution1.dataframe$delete_enrolment,10000 / Solution2.dataframe$delete_enrolment,10000 / Solution3.dataframe$delete_enrolment,10000 / Solution4.dataframe$delete_enrolment,names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Entities per second', outline=F, horizontal=F);
dev.off();
def.barplot.width=1; #bar width
def.barplot.sepwidth=0; #separation bar width
def.barplot.space=0; #space between bars
def.barplot.groupspace=1; #space between groups
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-Baseline.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Baseline.barplot.means=data.frame(insert=c(mean(Baseline.dataframe$insert_student), mean(Baseline.dataframe$insert_course), mean(Baseline.dataframe$insert_enrolment)),
update=c(mean(Baseline.dataframe$update_student), mean(Baseline.dataframe$update_course), mean(Baseline.dataframe$update_enrolment)),
delete=c(mean(Baseline.dataframe$delete_student), mean(Baseline.dataframe$delete_course), mean(Baseline.dataframe$delete_enrolment)));
barplot(as.matrix(Baseline.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-Solution1.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Solution1.barplot.means=data.frame(insert=c(mean(Solution1.dataframe$insert_student), mean(Solution1.dataframe$insert_course), mean(Solution1.dataframe$insert_enrolment)),
update=c(mean(Solution1.dataframe$update_student), mean(Solution1.dataframe$update_course), mean(Solution1.dataframe$update_enrolment)),
delete=c(mean(Solution1.dataframe$delete_student), mean(Solution1.dataframe$delete_course), mean(Solution1.dataframe$delete_enrolment)));
barplot(as.matrix(Solution1.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-Solution2.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Solution2.barplot.means=data.frame(insert=c(mean(Solution2.dataframe$insert_student), mean(Solution2.dataframe$insert_course), mean(Solution2.dataframe$insert_enrolment)),
update=c(mean(Solution2.dataframe$update_student), mean(Solution2.dataframe$update_course), mean(Solution2.dataframe$update_enrolment)),
delete=c(mean(Solution2.dataframe$delete_student), mean(Solution2.dataframe$delete_course), mean(Solution2.dataframe$delete_enrolment)));
barplot(as.matrix(Solution2.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-Solution3.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Solution3.barplot.means=data.frame(insert=c(mean(Solution3.dataframe$insert_student), mean(Solution3.dataframe$insert_course), mean(Solution3.dataframe$insert_enrolment)),
update=c(mean(Solution3.dataframe$update_student), mean(Solution3.dataframe$update_course), mean(Solution3.dataframe$update_enrolment)),
delete=c(mean(Solution3.dataframe$delete_student), mean(Solution3.dataframe$delete_course), mean(Solution3.dataframe$delete_enrolment)));
barplot(as.matrix(Solution3.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-Solution4.pdf');
par(las=1, mar=c(3,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

Solution4.barplot.means=data.frame(insert=c(mean(Solution4.dataframe$insert_student), mean(Solution4.dataframe$insert_course), mean(Solution4.dataframe$insert_enrolment)),
update=c(mean(Solution4.dataframe$update_student), mean(Solution4.dataframe$update_course), mean(Solution4.dataframe$update_enrolment)),
delete=c(mean(Solution4.dataframe$delete_student), mean(Solution4.dataframe$delete_course), mean(Solution4.dataframe$delete_enrolment)));
barplot(as.matrix(Solution4.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab = '');
mtext('insert', side=1, line=2, at=2.5);
mtext('update', side=1, line=2, at=6.5);
mtext('delete', side=1, line=2, at=10.5);
dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-insert-rt.pdf' );
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

insert.barplot.means=data.frame(Baseline = c(mean(Baseline.dataframe$insert_student), mean(Baseline.dataframe$insert_course), mean(Baseline.dataframe$insert_enrolment)), Solution1 = c(mean(Solution1.dataframe$insert_student), mean(Solution1.dataframe$insert_course), mean(Solution1.dataframe$insert_enrolment)), Solution2 = c(mean(Solution2.dataframe$insert_student), mean(Solution2.dataframe$insert_course), mean(Solution2.dataframe$insert_enrolment)), Solution3 = c(mean(Solution3.dataframe$insert_student), mean(Solution3.dataframe$insert_course), mean(Solution3.dataframe$insert_enrolment)), Solution4 = c(mean(Solution4.dataframe$insert_student), mean(Solution4.dataframe$insert_course), mean(Solution4.dataframe$insert_enrolment)));
barplot(as.matrix(insert.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-insert-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

insert.barplot.means=data.frame(Baseline = c(1000 / mean(Baseline.dataframe$insert_student), 1000 / mean(Baseline.dataframe$insert_course), 10000 / mean(Baseline.dataframe$insert_enrolment)), Solution1 = c(1000 / mean(Solution1.dataframe$insert_student), 1000 / mean(Solution1.dataframe$insert_course), 10000 / mean(Solution1.dataframe$insert_enrolment)), Solution2 = c(1000 / mean(Solution2.dataframe$insert_student), 1000 / mean(Solution2.dataframe$insert_course), 10000 / mean(Solution2.dataframe$insert_enrolment)), Solution3 = c(1000 / mean(Solution3.dataframe$insert_student), 1000 / mean(Solution3.dataframe$insert_course), 10000 / mean(Solution3.dataframe$insert_enrolment)), Solution4 = c(1000 / mean(Solution4.dataframe$insert_student), 1000 / mean(Solution4.dataframe$insert_course), 10000 / mean(Solution4.dataframe$insert_enrolment)));
barplot(as.matrix(insert.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Entities per second', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-update-rt.pdf' );
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

update.barplot.means=data.frame(Baseline = c(mean(Baseline.dataframe$update_student), mean(Baseline.dataframe$update_course), mean(Baseline.dataframe$update_enrolment)), Solution1 = c(mean(Solution1.dataframe$update_student), mean(Solution1.dataframe$update_course), mean(Solution1.dataframe$update_enrolment)), Solution2 = c(mean(Solution2.dataframe$update_student), mean(Solution2.dataframe$update_course), mean(Solution2.dataframe$update_enrolment)), Solution3 = c(mean(Solution3.dataframe$update_student), mean(Solution3.dataframe$update_course), mean(Solution3.dataframe$update_enrolment)), Solution4 = c(mean(Solution4.dataframe$update_student), mean(Solution4.dataframe$update_course), mean(Solution4.dataframe$update_enrolment)));
barplot(as.matrix(update.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-update-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

update.barplot.means=data.frame(Baseline = c(1000 / mean(Baseline.dataframe$update_student), 1000 / mean(Baseline.dataframe$update_course), 10000 / mean(Baseline.dataframe$update_enrolment)), Solution1 = c(1000 / mean(Solution1.dataframe$update_student), 1000 / mean(Solution1.dataframe$update_course), 10000 / mean(Solution1.dataframe$update_enrolment)), Solution2 = c(1000 / mean(Solution2.dataframe$update_student), 1000 / mean(Solution2.dataframe$update_course), 10000 / mean(Solution2.dataframe$update_enrolment)), Solution3 = c(1000 / mean(Solution3.dataframe$update_student), 1000 / mean(Solution3.dataframe$update_course), 10000 / mean(Solution3.dataframe$update_enrolment)), Solution4 = c(1000 / mean(Solution4.dataframe$update_student), 1000 / mean(Solution4.dataframe$update_course), 10000 / mean(Solution4.dataframe$update_enrolment)));
barplot(as.matrix(update.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Entities per second', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-delete-rt.pdf' );
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

delete.barplot.means=data.frame(Baseline = c(mean(Baseline.dataframe$delete_student), mean(Baseline.dataframe$delete_course), mean(Baseline.dataframe$delete_enrolment)), Solution1 = c(mean(Solution1.dataframe$delete_student), mean(Solution1.dataframe$delete_course), mean(Solution1.dataframe$delete_enrolment)), Solution2 = c(mean(Solution2.dataframe$delete_student), mean(Solution2.dataframe$delete_course), mean(Solution2.dataframe$delete_enrolment)), Solution3 = c(mean(Solution3.dataframe$delete_student), mean(Solution3.dataframe$delete_course), mean(Solution3.dataframe$delete_enrolment)), Solution4 = c(mean(Solution4.dataframe$delete_student), mean(Solution4.dataframe$delete_course), mean(Solution4.dataframe$delete_enrolment)));
barplot(as.matrix(delete.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Time (s)', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-delete-tp.pdf');
par(las=1, mar=c(3,4.2,0.5,0), cex.axis=1.1, cex.lab=1.3);

delete.barplot.means=data.frame(Baseline = c(1000 / mean(Baseline.dataframe$delete_student), 1000 / mean(Baseline.dataframe$delete_course), 10000 / mean(Baseline.dataframe$delete_enrolment)), Solution1 = c(1000 / mean(Solution1.dataframe$delete_student), 1000 / mean(Solution1.dataframe$delete_course), 10000 / mean(Solution1.dataframe$delete_enrolment)), Solution2 = c(1000 / mean(Solution2.dataframe$delete_student), 1000 / mean(Solution2.dataframe$delete_course), 10000 / mean(Solution2.dataframe$delete_enrolment)), Solution3 = c(1000 / mean(Solution3.dataframe$delete_student), 1000 / mean(Solution3.dataframe$delete_course), 10000 / mean(Solution3.dataframe$delete_enrolment)), Solution4 = c(1000 / mean(Solution4.dataframe$delete_student), 1000 / mean(Solution4.dataframe$delete_course), 10000 / mean(Solution4.dataframe$delete_enrolment)));
barplot(as.matrix(delete.barplot.means),beside=T, names=c('s', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e', 's', 'c', 'e'), ylab='Entities per second', xlab='');
mtext('Baseline', side=1, line=2, at=2.5);
mtext('Solution1', side=1, line=2, at=6.5);
mtext('Solution2', side=1, line=2, at=10.5);
mtext('Solution3', side=1, line=2, at=14.5);
mtext('Solution4', side=1, line=2, at=18.5);

dev.off();
pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-insert_student-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(57441.68406666668, 80999.42993333335, 94933.78593333332, 339430.45660000003, 51153.0982), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-insert_student-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.01740896034384024, 0.012345765900118691, 0.010533657645363938, 0.002946111583553165, 0.019549158021478354), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-insert_course-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(50734.876599999996, 76304.17893333334, 93997.05873333334, 349081.8334666667, 52693.262200000005), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-insert_course-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.01971030713022371, 0.013105442113120647, 0.01063863075585129, 0.0028646578083688463, 0.01897775841253571), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-insert_enrolment-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(454720.8410666666, 1731894.577066667, 1837791.4252000002, 5291214.002199999, 2166041.2566), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-insert_enrolment-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.021991514566480803, 0.005774023507214356, 0.005441313885177006, 0.0018899254492148996, 0.00461671723450773), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-update_student-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(99741.5504, 2803314.6404, 2916659.404066667, 7291286.2321333345, 3166872.5360666667), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-update_student-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.01002591192927757, 3.5672057127961626E-4, 3.4285799658530946E-4, 1.3715001279100973E-4, 3.157689451063365E-4), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-update_course-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(105542.09486666667, 543554.3405333334, 509769.3286, 1071752.4700666666, 451761.81379999995), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-update_course-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.009474892470756042, 0.0018397424607423868, 0.0019616715716230717, 9.330512669010193E-4, 0.0022135558372862194), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-update_enrolment-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(612375.5871333333, 1920145.3752, 2009733.4496, 5484660.4442, 2291695.2528), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-update_enrolment-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.016329847580652635, 0.005207939007721441, 0.004975784227500574, 0.0018232669281422788, 0.004363581932537483), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-delete_student-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(45017.045333333335, 1111522.4503333336, 1429544.2155999998, 4282859.669266666, 894717.3185333334), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-delete_student-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.0222138079608601, 8.996669385311208E-4, 6.995236587210322E-4, 2.3348885492931065E-4, 0.0011176714469316985), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-delete_course-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(44586.46006666666, 349633.5293333334, 371881.13273333333, 661787.1300666666, 369145.5257333334), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-delete_course-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.02242833359061872, 0.002860137590083989, 0.0026890312844052647, 0.0015110599081902102, 0.002708958744694061), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-delete_enrolment-rt.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(449643.86399999994, 625044.1430666667, 986092.6372000001, 3535680.9787333333, 455603.78540000005), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Time in seconds');
dev.off();

pdf('/u/students/subramhars/workspace/ResearchTestApplication/logs/barplot-delete_enrolment-tp.pdf');
par(las=1, mar=c(2.5,4.2,2,0), cex.axis=1.1, cex.lab=1.3);

barplot(c(0.022239823114766227, 0.015998870017302776, 0.010141035053658749, 0.002828309471399913, 0.0219488957740341), names=c('Baseline', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), col=gray.colors(5), xlab='', ylab='Entities per second');
dev.off();


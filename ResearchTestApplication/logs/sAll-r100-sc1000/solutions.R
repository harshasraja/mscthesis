Solution0.dataframe = data.frame(
insert_user=c(761, 574, 661, 533, 537, 510, 532, 526, 581, 538, 531, 551, 562, 550, 529, 561, 562, 554, 536, 593),
insert_course=c(635, 536, 547, 544, 562, 538, 632, 536, 611, 537, 600, 548, 557, 573, 541, 571, 598, 565, 601, 557),
insert_enrolment=c(5327, 5305, 5219, 5127, 5152, 5052, 5480, 5061, 5087, 5168, 5163, 5275, 5163, 5178, 5150, 5194, 5205, 5314, 5132, 5137),
update_user=c(1088, 1061, 1043, 1078, 1123, 1117, 1143, 1139, 1096, 1120, 1112, 1135, 1096, 1086, 1098, 1120, 1284, 1121, 1169, 1115),
update_course=c(1203, 1170, 1167, 1217, 1191, 1147, 1247, 1249, 1400, 1191, 1291, 1274, 1260, 1229, 1226, 1231, 1441, 1236, 1343, 1374),
update_enrolment=c(6468, 6474, 6495, 6889, 6903, 6672, 6924, 6599, 6673, 6524, 6622, 6657, 7293, 6684, 6688, 6796, 6646, 7243, 6603, 6494),
delete_user=c(502, 492, 520, 512, 557, 576, 573, 526, 523, 540, 535, 531, 543, 544, 529, 538, 545, 538, 554, 551),
delete_course=c(507, 514, 539, 601, 515, 533, 544, 569, 591, 559, 573, 568, 579, 553, 562, 581, 633, 604, 580, 612),
delete_enrolment=c(5367, 5187, 5216, 5245, 5135, 5169, 5465, 5062, 5909, 5209, 5231, 5355, 6389, 5185, 5228, 5343, 5159, 6464, 5276, 5268));

Solution1.dataframe = data.frame(
insert_user=c(529, 522, 522, 564, 637, 535, 584, 548, 570, 549, 543, 543, 594, 537, 539, 558, 609, 567, 574, 558),
insert_course=c(491, 535, 602, 545, 522, 541, 591, 538, 608, 547, 564, 582, 545, 558, 547, 550, 586, 608, 571, 565),
insert_enrolment=c(15524, 15095, 15045, 15124, 14840, 15411, 14923, 15077, 15068, 15441, 15640, 15399, 15087, 15247, 15229, 15743, 15339, 15609, 15724, 15813),
update_user=c(25145, 25659, 25203, 25117, 25036, 26496, 25265, 25809, 25577, 25589, 27592, 28318, 26755, 25310, 25527, 28596, 26120, 28215, 26586, 25952),
update_course=c(3931, 3891, 3744, 3978, 3947, 3914, 3993, 4007, 4162, 4097, 4975, 3943, 4329, 4040, 4121, 4122, 4111, 4300, 4321, 4228),
update_enrolment=c(16374, 16800, 16339, 16585, 16548, 16802, 16452, 16656, 16392, 16539, 17148, 16567, 16454, 16646, 16527, 17252, 16827, 17327, 17179, 17312),
delete_user=c(7900, 8171, 9297, 7965, 8303, 8755, 7992, 8121, 8173, 8105, 8171, 8261, 8001, 8518, 8356, 7980, 8957, 8408, 8280, 8121),
delete_course=c(1589, 1576, 1633, 1655, 1636, 1773, 1668, 1691, 1716, 1713, 1711, 1826, 1774, 1781, 1767, 1730, 1914, 1835, 1841, 1727),
delete_enrolment=c(5134, 5372, 5286, 5290, 5253, 6058, 5241, 5362, 5246, 5342, 5756, 6198, 5469, 5285, 5306, 6158, 5846, 5946, 5549, 5471));

Solution2.dataframe = data.frame(
insert_user=c(1032, 1049, 1101, 1097, 1126, 1126, 1076, 1055, 1152, 1059, 1085, 1113, 1073, 1076, 1088, 1062, 1312, 1342, 1291, 1241),
insert_course=c(1063, 1091, 1090, 1105, 1089, 1102, 1074, 1096, 1148, 1093, 1106, 1107, 1259, 1282, 1283, 1235, 1398, 1386, 1302, 1303),
insert_enrolment=c(30877, 30788, 32790, 34107, 33320, 33153, 33086, 33140, 32988, 32801, 32645, 32955, 34967, 34895, 34872, 34873, 36939, 37481, 38088, 36893),
update_user=c(18098, 18392, 20987, 23517, 21362, 21254, 20853, 21178, 20842, 20932, 22879, 21051, 22652, 22108, 22754, 20769, 22006, 22790, 22737, 23565),
update_course=c(6734, 6819, 7016, 7302, 7357, 7499, 7274, 7579, 7196, 7339, 7343, 7250, 7980, 8001, 7969, 7786, 8014, 8467, 8417, 8048),
update_enrolment=c(32101, 31953, 34494, 35151, 34241, 34236, 35090, 34037, 34231, 34254, 34827, 35735, 36599, 36080, 36680, 35604, 38459, 38406, 38533, 39537),
delete_user=c(14808, 14773, 16938, 17203, 17011, 16823, 16822, 16958, 18282, 17398, 17319, 16955, 16910, 18125, 16957, 17349, 17514, 17387, 17397, 17137),
delete_course=c(2542, 2581, 3044, 3063, 2902, 2984, 2987, 2976, 3050, 3026, 3037, 3125, 3268, 3307, 3235, 3204, 3181, 3205, 3289, 3262),
delete_enrolment=c(10116, 9873, 12165, 13600, 12078, 12395, 11936, 12232, 12213, 12131, 13073, 12019, 12979, 12597, 13095, 11987, 12114, 12627, 12513, 12987));

Solution3.dataframe = data.frame(
insert_user=c(1739, 1682, 1775, 1696, 1733, 1729, 1737, 1719, 1790, 1734, 1756, 1783, 1759, 1744, 1757, 1750, 1843, 1729, 1751, 1768),
insert_course=c(1748, 1737, 1779, 1766, 1778, 1766, 1869, 1815, 1816, 1790, 1804, 1874, 1809, 1827, 1826, 1790, 1850, 1886, 1797, 1815),
insert_enrolment=c(35388, 35560, 35674, 35221, 35450, 35679, 35590, 35709, 35867, 35652, 35626, 35871, 35618, 35721, 35541, 36362, 35722, 35622, 35808, 35639),
update_user=c(64622, 67690, 66819, 66836, 65116, 66380, 69452, 65066, 68064, 64792, 66700, 66570, 68092, 70138, 64680, 69426, 64752, 64549, 71670, 64861),
update_course=c(9827, 9777, 9780, 9889, 9936, 9996, 10059, 9918, 10813, 9941, 10078, 10419, 10708, 10027, 10170, 11284, 10072, 10130, 10136, 10116),
update_enrolment=c(37296, 36865, 37128, 36875, 36837, 37066, 37203, 37024, 37708, 37024, 37063, 37238, 37407, 37699, 37119, 37628, 37021, 36928, 38115, 36929),
delete_user=c(21334, 22601, 21504, 21769, 21873, 21890, 21796, 21950, 22179, 21982, 21776, 21738, 21800, 21791, 22356, 21735, 23750, 21833, 21794, 23119),
delete_course=c(3178, 3347, 3493, 3331, 3374, 3403, 3381, 3352, 3377, 3418, 3463, 3414, 3419, 3481, 3465, 3449, 3502, 3405, 3515, 3479),
delete_enrolment=c(17714, 18602, 18807, 18404, 18559, 18326, 19169, 18207, 18920, 18704, 18378, 18397, 18861, 19267, 17976, 18800, 18000, 17995, 19522, 18082));

Solution4.dataframe = data.frame(
insert_user=c(812, 668, 515, 536, 523, 513, 563, 529, 574, 559, 544, 548, 551, 571, 581, 661, 614, 548, 557, 530),
insert_course=c(655, 697, 533, 522, 561, 507, 528, 537, 556, 551, 615, 545, 617, 561, 564, 648, 568, 591, 632, 565),
insert_enrolment=c(15809, 16147, 14351, 14558, 14224, 14320, 14803, 14855, 14757, 14878, 15092, 14807, 14940, 14942, 15037, 14886, 14818, 14754, 14820, 14717),
update_user=c(26359, 27842, 26014, 25780, 34974, 26303, 26398, 30861, 26571, 26572, 32934, 26705, 26814, 29239, 26270, 27105, 27082, 27135, 27226, 26379),
update_course=c(5916, 4602, 4648, 4457, 4470, 4723, 4635, 4593, 4595, 4655, 6941, 4646, 4718, 5142, 4701, 5036, 4681, 4690, 4899, 4780),
update_enrolment=c(16221, 16301, 15739, 16110, 15700, 16036, 16191, 16438, 15888, 16114, 17671, 15898, 16476, 16692, 16185, 16286, 16135, 15858, 15918, 16022),
delete_user=c(16565, 16534, 16714, 16576, 17056, 17209, 17539, 17111, 17335, 17304, 17463, 17734, 17656, 17885, 17670, 20375, 17976, 18045, 19851, 17775),
delete_course=c(1428, 1556, 1507, 1527, 1761, 1477, 1566, 1714, 1613, 1638, 1751, 1627, 1657, 1673, 1590, 1816, 1657, 1671, 1656, 1756),
delete_enrolment=c(23613, 24205, 23111, 22711, 26017, 23942, 24139, 25485, 24166, 24112, 26360, 24993, 25096, 25647, 24227, 24906, 25456, 25412, 25611, 25526));

png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-insert_user.png', width=640, height=480);
boxplot(Solution0.dataframe$insert_user,Solution1.dataframe$insert_user,Solution2.dataframe$insert_user,Solution3.dataframe$insert_user,Solution4.dataframe$insert_user,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='insert_user', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-insert_course.png', width=640, height=480);
boxplot(Solution0.dataframe$insert_course,Solution1.dataframe$insert_course,Solution2.dataframe$insert_course,Solution3.dataframe$insert_course,Solution4.dataframe$insert_course,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='insert_course', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-insert_enrolment.png', width=640, height=480);
boxplot(Solution0.dataframe$insert_enrolment,Solution1.dataframe$insert_enrolment,Solution2.dataframe$insert_enrolment,Solution3.dataframe$insert_enrolment,Solution4.dataframe$insert_enrolment,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='insert_enrolment', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-update_user.png', width=640, height=480);
boxplot(Solution0.dataframe$update_user,Solution1.dataframe$update_user,Solution2.dataframe$update_user,Solution3.dataframe$update_user,Solution4.dataframe$update_user,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='update_user', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-update_course.png', width=640, height=480);
boxplot(Solution0.dataframe$update_course,Solution1.dataframe$update_course,Solution2.dataframe$update_course,Solution3.dataframe$update_course,Solution4.dataframe$update_course,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='update_course', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-update_enrolment.png', width=640, height=480);
boxplot(Solution0.dataframe$update_enrolment,Solution1.dataframe$update_enrolment,Solution2.dataframe$update_enrolment,Solution3.dataframe$update_enrolment,Solution4.dataframe$update_enrolment,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='update_enrolment', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-delete_user.png', width=640, height=480);
boxplot(Solution0.dataframe$delete_user,Solution1.dataframe$delete_user,Solution2.dataframe$delete_user,Solution3.dataframe$delete_user,Solution4.dataframe$delete_user,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='delete_user', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-delete_course.png', width=640, height=480);
boxplot(Solution0.dataframe$delete_course,Solution1.dataframe$delete_course,Solution2.dataframe$delete_course,Solution3.dataframe$delete_course,Solution4.dataframe$delete_course,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='delete_course', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/bp-delete_enrolment.png', width=640, height=480);
boxplot(Solution0.dataframe$delete_enrolment,Solution1.dataframe$delete_enrolment,Solution2.dataframe$delete_enrolment,Solution3.dataframe$delete_enrolment,Solution4.dataframe$delete_enrolment,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='delete_enrolment', outline=F);
dev.off();
def.barplot.width=1; #bar width
def.barplot.sepwidth=0; #separation bar width
def.barplot.space=0; #space between bars
def.barplot.groupspace=1; #space between groups
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/Solution0-barplot.png', width=640, height=480);
Solution0.barplot.means=c(mean(Solution0.dataframe$insert_user), mean(Solution0.dataframe$insert_course), mean(Solution0.dataframe$insert_enrolment), 0, mean(Solution0.dataframe$update_user), mean(Solution0.dataframe$update_course), mean(Solution0.dataframe$update_enrolment), 0, mean(Solution0.dataframe$delete_user), mean(Solution0.dataframe$delete_course), mean(Solution0.dataframe$delete_enrolment));
Solution0.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution0.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution0.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution0.barplot.means, width=Solution0.barplot.widths, space=Solution0.barplot.spaces, names=Solution0.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/Solution1-barplot.png', width=640, height=480);
Solution1.barplot.means=c(mean(Solution1.dataframe$insert_user), mean(Solution1.dataframe$insert_course), mean(Solution1.dataframe$insert_enrolment), 0, mean(Solution1.dataframe$update_user), mean(Solution1.dataframe$update_course), mean(Solution1.dataframe$update_enrolment), 0, mean(Solution1.dataframe$delete_user), mean(Solution1.dataframe$delete_course), mean(Solution1.dataframe$delete_enrolment));
Solution1.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution1.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution1.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution1.barplot.means, width=Solution1.barplot.widths, space=Solution1.barplot.spaces, names=Solution1.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/Solution2-barplot.png', width=640, height=480);
Solution2.barplot.means=c(mean(Solution2.dataframe$insert_user), mean(Solution2.dataframe$insert_course), mean(Solution2.dataframe$insert_enrolment), 0, mean(Solution2.dataframe$update_user), mean(Solution2.dataframe$update_course), mean(Solution2.dataframe$update_enrolment), 0, mean(Solution2.dataframe$delete_user), mean(Solution2.dataframe$delete_course), mean(Solution2.dataframe$delete_enrolment));
Solution2.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution2.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution2.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution2.barplot.means, width=Solution2.barplot.widths, space=Solution2.barplot.spaces, names=Solution2.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/Solution3-barplot.png', width=640, height=480);
Solution3.barplot.means=c(mean(Solution3.dataframe$insert_user), mean(Solution3.dataframe$insert_course), mean(Solution3.dataframe$insert_enrolment), 0, mean(Solution3.dataframe$update_user), mean(Solution3.dataframe$update_course), mean(Solution3.dataframe$update_enrolment), 0, mean(Solution3.dataframe$delete_user), mean(Solution3.dataframe$delete_course), mean(Solution3.dataframe$delete_enrolment));
Solution3.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution3.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution3.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution3.barplot.means, width=Solution3.barplot.widths, space=Solution3.barplot.spaces, names=Solution3.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/sAll-r100-sc1000/Solution4-barplot.png', width=640, height=480);
Solution4.barplot.means=c(mean(Solution4.dataframe$insert_user), mean(Solution4.dataframe$insert_course), mean(Solution4.dataframe$insert_enrolment), 0, mean(Solution4.dataframe$update_user), mean(Solution4.dataframe$update_course), mean(Solution4.dataframe$update_enrolment), 0, mean(Solution4.dataframe$delete_user), mean(Solution4.dataframe$delete_course), mean(Solution4.dataframe$delete_enrolment));
Solution4.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution4.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution4.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution4.barplot.means, width=Solution4.barplot.widths, space=Solution4.barplot.spaces, names=Solution4.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();

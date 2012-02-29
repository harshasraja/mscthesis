Solution0.dataframe = data.frame(
insert_user=c(775, 557, 548, 491, 547, 551, 510, 511, 588, 551, 530, 517, 570, 527, 584, 528, 571, 526, 540, 560),
insert_course=c(58, 45, 47, 48, 46, 47, 49, 51, 47, 56, 76, 47, 52, 49, 50, 54, 51, 60, 56, 50),
insert_enrolment=c(5399, 5111, 5034, 5126, 5066, 5061, 5079, 5076, 5023, 5075, 5145, 5186, 5220, 5117, 5152, 5182, 5159, 5242, 5161, 5125),
update_user=c(1047, 1047, 1011, 1044, 1067, 1119, 1096, 1102, 1114, 1114, 1146, 1083, 1108, 1124, 1119, 1105, 1172, 1129, 1130, 1138),
update_course=c(106, 135, 106, 111, 104, 106, 113, 110, 111, 173, 121, 110, 142, 113, 110, 106, 122, 117, 110, 115),
update_enrolment=c(6542, 6542, 6284, 6572, 6736, 6703, 6823, 6630, 6442, 6578, 6642, 6711, 7077, 6496, 6730, 6725, 6689, 6686, 6679, 6665),
delete_user=c(479, 519, 481, 512, 508, 506, 535, 542, 560, 503, 527, 509, 526, 518, 555, 525, 537, 550, 524, 575),
delete_course=c(47, 49, 44, 50, 59, 50, 48, 49, 56, 49, 50, 49, 54, 51, 49, 48, 51, 50, 52, 50),
delete_enrolment=c(5370, 5093, 4907, 5066, 5165, 5210, 5466, 5180, 5131, 5169, 5172, 5223, 6291, 5157, 5132, 5549, 5175, 5922, 5207, 5163));

Solution1.dataframe = data.frame(
insert_user=c(554, 513, 586, 537, 617, 530, 590, 535, 673, 557, 530, 548, 623, 581, 545, 666, 576, 555, 554, 541),
insert_course=c(48, 46, 54, 73, 47, 47, 53, 51, 51, 65, 56, 52, 88, 50, 51, 50, 50, 51, 66, 51),
insert_enrolment=c(15160, 14726, 15060, 15123, 15008, 15029, 15102, 15203, 15122, 15153, 15540, 15239, 15271, 15475, 15332, 15758, 15650, 15499, 15550, 15456),
update_user=c(24702, 24557, 25451, 25119, 25122, 25568, 25610, 26333, 25236, 25346, 25964, 29825, 25515, 25836, 27768, 25903, 26378, 30668, 25909, 26240),
update_course=c(991, 965, 987, 973, 975, 982, 984, 974, 1048, 961, 986, 981, 991, 958, 1048, 1016, 996, 974, 970, 976),
update_enrolment=c(16232, 16414, 16464, 16564, 16464, 16417, 16731, 16607, 16451, 16514, 16978, 16940, 16556, 16902, 16784, 16767, 17073, 17066, 16764, 16879),
delete_user=c(7878, 8067, 9372, 8330, 8049, 9040, 8075, 8046, 8053, 8367, 8372, 8254, 8289, 8263, 9934, 8222, 8368, 8286, 8504, 8337),
delete_course=c(197, 189, 235, 254, 210, 298, 193, 249, 239, 196, 199, 368, 200, 198, 311, 202, 204, 341, 254, 206),
delete_enrolment=c(5060, 5148, 5358, 5290, 5141, 5359, 5156, 5595, 5386, 5176, 5417, 6947, 5385, 5344, 6149, 5396, 5417, 6614, 5301, 5370));

Solution2.dataframe = data.frame(
insert_user=c(1150, 1155, 1112, 1043, 1138, 1077, 1088, 1128, 1233, 1096, 1090, 1087, 1111, 1102, 1113, 1145, 1342, 1317, 1350, 1312),
insert_course=c(164, 105, 114, 104, 129, 157, 111, 106, 124, 110, 107, 105, 109, 151, 104, 110, 115, 108, 115, 107),
insert_enrolment=c(31319, 31412, 33189, 33278, 33416, 33717, 33759, 33582, 32882, 32829, 32943, 32920, 34176, 33991, 33967, 33533, 35990, 35902, 35283, 35274),
update_user=c(18654, 18628, 21290, 21180, 21633, 22799, 22651, 22878, 21641, 20690, 20913, 23198, 24353, 23448, 24025, 22261, 23785, 23430, 23568, 21671),
update_course=c(1081, 937, 1054, 1005, 1071, 1011, 992, 1920, 1046, 1007, 1002, 1883, 1356, 1036, 1047, 1058, 1008, 1052, 1101, 1026),
update_enrolment=c(32473, 31971, 34980, 34867, 34735, 34880, 35155, 35035, 34241, 34248, 34313, 35508, 36020, 35538, 35417, 35871, 37406, 37308, 37203, 36456),
delete_user=c(14945, 14913, 16902, 18475, 17251, 17330, 17329, 16699, 16816, 17169, 16976, 17667, 17580, 17550, 16922, 17237, 17369, 17016, 16994, 18141),
delete_course=c(300, 316, 308, 379, 336, 387, 407, 416, 375, 365, 337, 381, 406, 397, 445, 326, 397, 377, 390, 359),
delete_enrolment=c(10216, 10052, 12111, 11994, 12167, 13325, 12858, 12887, 12873, 11882, 11886, 13246, 13926, 13376, 13587, 12053, 12987, 12563, 12875, 11770));

Solution3.dataframe = data.frame(
insert_user=c(1752, 1764, 1747, 1695, 1799, 1769, 1771, 1717, 1763, 1724, 1737, 1747, 1712, 1779, 1721, 1772, 1736, 1699, 1721, 1685),
insert_course=c(194, 168, 189, 169, 165, 171, 180, 199, 187, 217, 173, 166, 172, 173, 167, 174, 173, 178, 223, 166),
insert_enrolment=c(36004, 36095, 35489, 35773, 35880, 35911, 36107, 35857, 35631, 35890, 35390, 35247, 35931, 35408, 35354, 35437, 35638, 35927, 35990, 35763),
update_user=c(64608, 64547, 66424, 64847, 66012, 66434, 65640, 67250, 64965, 68784, 66184, 65817, 68228, 67407, 64798, 63669, 66950, 65121, 64710, 70129),
update_course=c(876, 877, 1220, 829, 1029, 865, 827, 892, 851, 851, 841, 818, 1538, 821, 911, 818, 851, 980, 891, 1615),
update_enrolment=c(37094, 37659, 37241, 37204, 37284, 37251, 37456, 37335, 37516, 37657, 37881, 36669, 37245, 36175, 36945, 36547, 37850, 36857, 37154, 37671),
delete_user=c(21823, 21831, 22741, 22050, 21828, 23641, 21868, 22103, 21869, 21669, 21930, 21410, 21589, 21649, 22079, 21685, 22315, 21841, 21632, 21234),
delete_course=c(352, 347, 431, 378, 392, 510, 389, 429, 360, 430, 471, 420, 450, 413, 445, 384, 454, 440, 368, 451),
delete_enrolment=c(17855, 18092, 18606, 18083, 18392, 18738, 18117, 18934, 17990, 18994, 18180, 18402, 18849, 18552, 18270, 17730, 18463, 17774, 17747, 18873));

Solution4.dataframe = data.frame(
insert_user=c(535, 542, 592, 536, 556, 554, 534, 540, 563, 533, 546, 554, 565, 580, 540, 555, 612, 588, 552, 545),
insert_course=c(52, 50, 51, 49, 47, 49, 50, 48, 74, 94, 48, 61, 50, 51, 74, 49, 51, 51, 57, 50),
insert_enrolment=c(14209, 14185, 13735, 14228, 14692, 14285, 14493, 14453, 14413, 14191, 14320, 14007, 14429, 14629, 14198, 14056, 14445, 14256, 14984, 14403),
update_user=c(25364, 28961, 25717, 26851, 25608, 27100, 29041, 27500, 27937, 27359, 26472, 27399, 28492, 28623, 28476, 27340, 26889, 27008, 29412, 29262),
update_course=c(706, 547, 605, 804, 621, 851, 644, 559, 571, 706, 558, 867, 1100, 1067, 1019, 737, 621, 629, 750, 557),
update_enrolment=c(15430, 15951, 15315, 15970, 15858, 15786, 16325, 15984, 15670, 15792, 15808, 15599, 16206, 16016, 16093, 15732, 16039, 15706, 15818, 15924),
delete_user=c(16243, 16872, 16457, 16754, 17741, 17553, 16898, 17248, 17766, 19560, 17618, 17485, 18715, 18179, 18052, 18939, 18329, 17503, 16782, 16938),
delete_course=c(171, 235, 173, 248, 269, 244, 232, 229, 252, 302, 240, 231, 269, 274, 257, 220, 235, 245, 262, 251),
delete_enrolment=c(22656, 23927, 23092, 24513, 23699, 24306, 24476, 25047, 24233, 25352, 24618, 25082, 25458, 24998, 25639, 25932, 25513, 26075, 24306, 24361));

png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-insert_user.png', width=640, height=480);
boxplot(Solution0.dataframe$insert_user,Solution1.dataframe$insert_user,Solution2.dataframe$insert_user,Solution3.dataframe$insert_user,Solution4.dataframe$insert_user,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='insert_user', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-insert_course.png', width=640, height=480);
boxplot(Solution0.dataframe$insert_course,Solution1.dataframe$insert_course,Solution2.dataframe$insert_course,Solution3.dataframe$insert_course,Solution4.dataframe$insert_course,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='insert_course', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-insert_enrolment.png', width=640, height=480);
boxplot(Solution0.dataframe$insert_enrolment,Solution1.dataframe$insert_enrolment,Solution2.dataframe$insert_enrolment,Solution3.dataframe$insert_enrolment,Solution4.dataframe$insert_enrolment,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='insert_enrolment', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-update_user.png', width=640, height=480);
boxplot(Solution0.dataframe$update_user,Solution1.dataframe$update_user,Solution2.dataframe$update_user,Solution3.dataframe$update_user,Solution4.dataframe$update_user,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='update_user', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-update_course.png', width=640, height=480);
boxplot(Solution0.dataframe$update_course,Solution1.dataframe$update_course,Solution2.dataframe$update_course,Solution3.dataframe$update_course,Solution4.dataframe$update_course,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='update_course', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-update_enrolment.png', width=640, height=480);
boxplot(Solution0.dataframe$update_enrolment,Solution1.dataframe$update_enrolment,Solution2.dataframe$update_enrolment,Solution3.dataframe$update_enrolment,Solution4.dataframe$update_enrolment,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='update_enrolment', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-delete_user.png', width=640, height=480);
boxplot(Solution0.dataframe$delete_user,Solution1.dataframe$delete_user,Solution2.dataframe$delete_user,Solution3.dataframe$delete_user,Solution4.dataframe$delete_user,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='delete_user', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-delete_course.png', width=640, height=480);
boxplot(Solution0.dataframe$delete_course,Solution1.dataframe$delete_course,Solution2.dataframe$delete_course,Solution3.dataframe$delete_course,Solution4.dataframe$delete_course,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='delete_course', outline=F);
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/bp-delete_enrolment.png', width=640, height=480);
boxplot(Solution0.dataframe$delete_enrolment,Solution1.dataframe$delete_enrolment,Solution2.dataframe$delete_enrolment,Solution3.dataframe$delete_enrolment,Solution4.dataframe$delete_enrolment,names=c('Solution0', 'Solution1', 'Solution2', 'Solution3', 'Solution4'), xlab='Solutions', ylab='Time (ms)', main='delete_enrolment', outline=F);
dev.off();
def.barplot.width=1; #bar width
def.barplot.sepwidth=0; #separation bar width
def.barplot.space=0; #space between bars
def.barplot.groupspace=1; #space between groups
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/Solution0-barplot.png', width=640, height=480);
Solution0.barplot.means=c(mean(Solution0.dataframe$insert_user), mean(Solution0.dataframe$insert_course), mean(Solution0.dataframe$insert_enrolment), 0, mean(Solution0.dataframe$update_user), mean(Solution0.dataframe$update_course), mean(Solution0.dataframe$update_enrolment), 0, mean(Solution0.dataframe$delete_user), mean(Solution0.dataframe$delete_course), mean(Solution0.dataframe$delete_enrolment));
Solution0.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution0.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution0.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution0.barplot.means, width=Solution0.barplot.widths, space=Solution0.barplot.spaces, names=Solution0.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/Solution1-barplot.png', width=640, height=480);
Solution1.barplot.means=c(mean(Solution1.dataframe$insert_user), mean(Solution1.dataframe$insert_course), mean(Solution1.dataframe$insert_enrolment), 0, mean(Solution1.dataframe$update_user), mean(Solution1.dataframe$update_course), mean(Solution1.dataframe$update_enrolment), 0, mean(Solution1.dataframe$delete_user), mean(Solution1.dataframe$delete_course), mean(Solution1.dataframe$delete_enrolment));
Solution1.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution1.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution1.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution1.barplot.means, width=Solution1.barplot.widths, space=Solution1.barplot.spaces, names=Solution1.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/Solution2-barplot.png', width=640, height=480);
Solution2.barplot.means=c(mean(Solution2.dataframe$insert_user), mean(Solution2.dataframe$insert_course), mean(Solution2.dataframe$insert_enrolment), 0, mean(Solution2.dataframe$update_user), mean(Solution2.dataframe$update_course), mean(Solution2.dataframe$update_enrolment), 0, mean(Solution2.dataframe$delete_user), mean(Solution2.dataframe$delete_course), mean(Solution2.dataframe$delete_enrolment));
Solution2.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution2.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution2.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution2.barplot.means, width=Solution2.barplot.widths, space=Solution2.barplot.spaces, names=Solution2.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/Solution3-barplot.png', width=640, height=480);
Solution3.barplot.means=c(mean(Solution3.dataframe$insert_user), mean(Solution3.dataframe$insert_course), mean(Solution3.dataframe$insert_enrolment), 0, mean(Solution3.dataframe$update_user), mean(Solution3.dataframe$update_course), mean(Solution3.dataframe$update_enrolment), 0, mean(Solution3.dataframe$delete_user), mean(Solution3.dataframe$delete_course), mean(Solution3.dataframe$delete_enrolment));
Solution3.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution3.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution3.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution3.barplot.means, width=Solution3.barplot.widths, space=Solution3.barplot.spaces, names=Solution3.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();
png('/home/phoenix1/subramhars/workspace/ResearchTestApplication/logs/backup/first-real-run/Solution4-barplot.png', width=640, height=480);
Solution4.barplot.means=c(mean(Solution4.dataframe$insert_user), mean(Solution4.dataframe$insert_course), mean(Solution4.dataframe$insert_enrolment), 0, mean(Solution4.dataframe$update_user), mean(Solution4.dataframe$update_course), mean(Solution4.dataframe$update_enrolment), 0, mean(Solution4.dataframe$delete_user), mean(Solution4.dataframe$delete_course), mean(Solution4.dataframe$delete_enrolment));
Solution4.barplot.widths=c(rep(def.barplot.width, 3), def.barplot.sepwidth, rep(def.barplot.width, 3), def.barplot.sepwidth);
Solution4.barplot.spaces=c(rep(def.barplot.space,3), def.barplot.groupspace, rep(def.barplot.space,3), def.barplot.groupspace);
Solution4.barplot.names=c('u', 'c', 'e', '', 'u', 'c', 'e', '', 'u', 'c', 'e');
barplot(Solution4.barplot.means, width=Solution4.barplot.widths, space=Solution4.barplot.spaces, names=Solution4.barplot.names,xlab=paste('insert','update','delete', sep=' '));
dev.off();

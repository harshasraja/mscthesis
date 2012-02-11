solution0_df = data.frame(
insert_user=c(68, 12, 11, 10, 10),
insert_course=c(25, 15, 14, 10, 10),
insert_enrolment=c(0, 0, 0, 0, 0),
update_course=c(81, 46, 41, 43, 35),
update_enrolment=c(0, 0, 0, 0, 0),
delete_enrolment=c(0, 0, 0, 0, 0),
delete_course=c(9, 9, 12, 9, 22),
delete_user=c(11, 9, 9, 9, 11));

solution1_df = data.frame(
insert_user=c(10225, 11, 11, 13, 12),
insert_course=c(10045, 10, 11, 10, 33),
insert_enrolment=c(0, 0, 0, 0, 0),
update_course=c(202, 85, 142, 101, 94),
update_enrolment=c(0, 0, 0, 0, 0),
delete_enrolment=c(0, 0, 0, 0, 0),
delete_course=c(32, 33, 76, 78, 35),
delete_user=c(35, 52, 45, 47, 55));

png('/tmp/bp-insert_user.png', width=640, height=480);
boxplot(solution0_df$insert_user,solution1_df$insert_user,names=c('solution0', 'solution1'), xlab='Solutions', ylab='Time (ms)', main='insert_user', outline=F);
dev.off();
png('/tmp/bp-insert_course.png', width=640, height=480);
boxplot(solution0_df$insert_course,solution1_df$insert_course,names=c('solution0', 'solution1'), xlab='Solutions', ylab='Time (ms)', main='insert_course', outline=F);
dev.off();
png('/tmp/bp-insert_enrolment.png', width=640, height=480);
boxplot(solution0_df$insert_enrolment,solution1_df$insert_enrolment,names=c('solution0', 'solution1'), xlab='Solutions', ylab='Time (ms)', main='insert_enrolment', outline=F);
dev.off();
png('/tmp/bp-update_course.png', width=640, height=480);
boxplot(solution0_df$update_course,solution1_df$update_course,names=c('solution0', 'solution1'), xlab='Solutions', ylab='Time (ms)', main='update_course', outline=F);
dev.off();
png('/tmp/bp-update_enrolment.png', width=640, height=480);
boxplot(solution0_df$update_enrolment,solution1_df$update_enrolment,names=c('solution0', 'solution1'), xlab='Solutions', ylab='Time (ms)', main='update_enrolment', outline=F);
dev.off();
png('/tmp/bp-delete_enrolment.png', width=640, height=480);
boxplot(solution0_df$delete_enrolment,solution1_df$delete_enrolment,names=c('solution0', 'solution1'), xlab='Solutions', ylab='Time (ms)', main='delete_enrolment', outline=F);
dev.off();
png('/tmp/bp-delete_course.png', width=640, height=480);
boxplot(solution0_df$delete_course,solution1_df$delete_course,names=c('solution0', 'solution1'), xlab='Solutions', ylab='Time (ms)', main='delete_course', outline=F);
dev.off();
png('/tmp/bp-delete_user.png', width=640, height=480);
boxplot(solution0_df$delete_user,solution1_df$delete_user,names=c('solution0', 'solution1'), xlab='Solutions', ylab='Time (ms)', main='delete_user', outline=F);
dev.off();

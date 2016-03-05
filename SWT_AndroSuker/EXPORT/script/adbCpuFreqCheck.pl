# command line parameter handling
# created by suker 2014.11.05
# cpu0~cpu3 online check
# cpu0-cpu3 freq
use strict;
use warnings;

my $argPath = "";
my $dirpath = "";
my $filename = "";

my $cpu0_online = "/sys/devices/system/cpu/cpu0/online";
my $cpu1_online = "/sys/devices/system/cpu/cpu1/online";
my $cpu2_online = "/sys/devices/system/cpu/cpu2/online";
my $cpu3_online = "/sys/devices/system/cpu/cpu3/online";

my $cpu0_cur_freq = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_cur_freq";
my $cpu1_cur_freq = "/sys/devices/system/cpu/cpu1/cpufreq/cpuinfo_cur_freq";
my $cpu2_cur_freq = "/sys/devices/system/cpu/cpu2/cpufreq/cpuinfo_cur_freq";
my $cpu3_cur_freq = "/sys/devices/system/cpu/cpu3/cpufreq/cpuinfo_cur_freq";
	
if ($#ARGV > -1)	{
	if ($ARGV[0] ne "none")	{
		$filename = $ARGV[0];
	}
	$argPath = $dirpath . $filename;
} 
chmod(0666, $argPath);

#online check
system("adb shell cat $cpu0_online > $argPath");	
system("adb shell cat $cpu1_online >> $argPath");	
system("adb shell cat $cpu2_online >> $argPath");	
system("adb shell cat $cpu3_online >> $argPath");	
#freq check
system("adb shell cat $cpu0_cur_freq >> $argPath");	
system("adb shell cat $cpu1_cur_freq >> $argPath");	
system("adb shell cat $cpu2_cur_freq >> $argPath");	
system("adb shell cat $cpu3_cur_freq >> $argPath");	

# command line parameter handling
# created by suker 2014.11.05
# cpu0~cpu3 online check
# cpu0-cpu3 freq
use strict;
use warnings;

my $argPath = "";
my $dirpath = "";
my $filename = "";

my $pa_therm0 = "/sys/class/hwmon/hwmon0/device/pa_therm0";
my $zone0_therm = "/sys/devices/virtual/thermal/thermal_zone5/temp";
my $zone1_therm = "/sys/devices/virtual/thermal/thermal_zone4/temp";

my $cpu01_label = "";
my $cpu01_body = "";
my $cpu01 = "";
my $cpu23_label = "";
my $cpu23_body = "";
my $cpu23 = "";

if ($#ARGV > -1)	{
	if ($ARGV[0] ne "none")	{
		$filename = $ARGV[0];
	}	
	$argPath = $dirpath . $filename;
} 
chmod(0666, $argPath);

#thermal check
system("echo 1 > $argPath");
open (my $fh, '>', $argPath);
my $pathermResult = `adb shell cat $pa_therm0`;
chomp($pathermResult);
print $fh $pathermResult;

$cpu01_label = " cpu0-1 thermal : ";
$cpu01_body = `adb shell cat $zone0_therm`;
$cpu01 = $cpu01_label . $cpu01_body;
$cpu23_label = " cpu2-3 thermal : ";
$cpu23_body = `adb shell cat $zone1_therm`;
$cpu23 = $cpu23_label . $cpu23_body;

#print $cpu01;
#print $cpu23;
print $fh `echo $cpu01 >> $argPath`;
print $fh `echo $cpu23 >> $argPath`;
close($fh);


# command line parameter handling
# if there was no parameter, "-v time" will be used
# parameter explain
# directory path, file name, display log, filter name, filter range, other slient, filter option

$arg = "";
$argPath = "";

$dirpath = ".\\";			#default : current path
$dummyfilename = "";

if ($ARGV[0] ne "")	{
	$dummyfilename = $ARGV[0];
}

$argPath = $dirpath . $dummyfilename;

exec("adb pull data/logger/kernel.log $argPath");	
#exec("adb shell cat proc/kmsg > $argPath");
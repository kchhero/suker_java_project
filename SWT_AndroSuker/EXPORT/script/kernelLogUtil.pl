
# command line parameter handling

$argPath = "";

# make timestamp string
($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)=localtime(time);

$dirpath = "./kernellog_out/";			#default : current path
$filename = sprintf ("%4d%02d%02d_%02d%02d%02d.txt", $year+1900,$mon+1,$mday,$hour,$min,$sec);	#default : current date and time				
$isWantStdoutLog = "false";	#default : false
$filtername = "none";

if ($#ARGV > 0)	{
	if ($ARGV[0] ne "none")	{	#dirpath
		$dirpath = $ARGV[0];
		$dirpath .= "/";
	}		
	if ($ARGV[1] ne "none")	{	#filename
		$filename = $ARGV[1];
	}
	if ($ARGV[2] ne "none")	{	#filtername
		$filtername = $ARGV[2];
	}
	$isWantStdoutLog = $ARGV[3];
	$argPath = $dirpath . $filename;
}

chmod(0666, $argPath);

# run adb logcat
if ($isWantStdoutLog eq "true")	{
	if ($filtername ne "none")	{
		print "adb shell cat /dev/kmsg | tee $argPath | grep $filtername \n";	
		exec("adb shell cat /dev/kmsg | tee $argPath | grep $filtername");
	} else {
		print "adb shell cat /dev/kmsg | tee $argPath \n";	
		exec("adb shell cat /dev/kmsg | tee $argPath");	
	}
} else	{
	print "kernel log doing....\n";
	print "kernel log saving to the file : $argPath \n";
	if ($filtername ne "none")	{
		exec("adb shell cat /dev/kmsg > $argPath | grep $filtername");
	} else {
		exec("adb shell cat /dev/kmsg > $argPath");
	}
}


# command line parameter handling
# if there was no parameter, "-v time" will be used
# parameter explain
# directory path, file name, display log, filter name, filter range, other slient, filter option

$arg = "";
$argPath = "";

# make timestamp string
($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)=localtime(time);

$dirpath = "./logcat_out/";			#default : current path
$filename = sprintf ("%4d%02d%02d_%02d%02d%02d.txt", $year+1900,$mon+1,$mday,$hour,$min,$sec);	#default : current date and time				
$isWantStdoutLog = "false";	#default : false
$filterName = "";		#default : "" none
$filterRange = "I";		#default : "I" - Info, V,D,I,W,E,F,S
$silentOtherLogs = "*:S";	#default : "*:S" - 다른 로그 출력안함
$filterOption = "-v time";	#default : time stamp
$optionSplitCnt = 0;
$logType = "";

if ($#ARGV > 0)	{
	if ($ARGV[0] ne "none")	{
		$dirpath = $ARGV[0];
		$dirpath .= "/";
	} 
		
	if ($ARGV[1] ne "none")	{
		$filename = $ARGV[1];
	}
	
	$isWantStdoutLog = $ARGV[2];
	
	if ($ARGV[3] eq "none")	{
		$filterName = "";
	} else	{
		$filterName = $ARGV[3];
	}
		
	if ($ARGV[4] eq "none")	{
		$filterRange = "";
	} else {
		$filterRange = $ARGV[4];
	}

	if ($ARGV[5] eq "false")	{
		$silentOtherLogs = "";
	}
	
	$optionSplitCnt = $ARGV[6];
	
	if ($ARGV[7] eq "main")	{
		$logType = "";
	} else {
		$logType = $ARGV[7];
	}
	
	if ($optionSplitCnt > 0)	{
		for ($i = 0; $i < $optionSplitCnt; $i++)
		{
			$filteroption .= $ARGV[8+$i];
			$filteroption .= " ";
		}
	}
	
	if ($filterName eq "" || $filterRange eq "")	{
		$arg = $filteroption;
	} else	{
		$arg = $filteroption . $filterName . ":" . $filterRange . " " . $silentOtherLogs;
	}
	$argPath = $dirpath . $filename;
} else {
	$arg = $filteroption;
}

chmod(0666, $argPath);

# run adb logcat
if ($isWantStdoutLog eq "true")	{
	print "adb logcat $logType $arg | tee $argPath \n";	
	exec("adb logcat $logType $arg | tee $argPath");	
} else	{
	print "logcat doing....\n";
	print "logcat saving to the file : $argPath \n";	
	exec("adb logcat $logType $arg > $argPath");	
}

#test-----
#print $filteroption . "\n";
#print $arg . "\n";
#print $argPath;
#exec("pause");
#--------

#adb logcat | grep xxxxx #우선순위 상관없이 특정 문자열을 포함하는 로그를 알고싶을때



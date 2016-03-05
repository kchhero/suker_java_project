# command line parameter handling
$arg = "";
$argPath = "";

$dirpath = "";
$command = "";
$filename = "";


if ($#ARGV > 0)	{
	if ($ARGV[0] ne "none")	{
		$command = $ARGV[0];
	} 
		
	if ($ARGV[1] ne "none")	{
		$filename = $ARGV[1];
	}
	$argPath = $dirpath . $filename;
} 
chmod(0666, $argPath);

#print "adb shell $command > $argPath\n";
exec("adb shell $command > $argPath");	

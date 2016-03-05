# command line parameter handling
use strict;
use warnings;

my $argPath = "";

my $dirpath = "";
my $filename = "";

my $topCmds1 = "top -m ";
my $topCmds2 = " -n 1 -d 1 -s cpu";
my $topCmds = "";
my $topListUpSize = "";

if ($#ARGV > 0)	{
	if ($ARGV[0] ne "none")	{
		$topListUpSize = $ARGV[0];
	} 
		
	if ($ARGV[1] ne "none")	{
		$filename = $ARGV[1];
	}
	$argPath = $dirpath . $filename;
} 
chmod(0666, $argPath);

#$topListUpSize ==> available more over 8, but error occured when less than 8 
$topCmds = $topCmds1 . $topListUpSize . $topCmds2;

exec("adb shell $topCmds > $argPath");	

#!/usr/bin/perl
use Cwd;

#arg0 ==> Ramdump Parser Location
#arg1 ==> Dump files Location
#arg2 ==> Vmlinux Location
#arg3 ==> ramdump parse batch file name
#arg4 ==> T32 running batch file name

$RamDumpParserBatLocation = "";
$DumpFilesLocation = "";
$VmlinuxLocation = "";
$RamDumpParseBatName = "";

$launchT32BatName = "";

if ($#ARGV > 0)	{
	if ($ARGV[1] eq "launch_t32.bat")	{
		$RamDumpParserBatLocation = $ARGV[0];
		$launchT32BatName = $ARGV[1];
		
		&go_T32;
	} else {
		$RamDumpParserBatLocation = $ARGV[0];
		$DumpFilesLocation = $ARGV[1];
		$VmlinuxLocation = $ARGV[2];
		$RamDumpParseBatName = $ARGV[3];
		
		&go_launch;
	}
} else {
	print "fuck... \n";
	print "processing fail!!!...\n";
	print "Press Enter key to exit..."; <STDIN>;
}

sub go_launch {	
	#----------------------------------------------------------------
	print "$RamDumpParserBatLocation \n";
	print "$DumpFilesLocation \n";
	print "$VmlinuxLocation \n";
 
	chdir "$RamDumpParserBatLocation" or die "error!!!\n";

	print "Please Wait... Do parsing";
	#system "$RamDumpParseBatName -a $DumpFilesLocation -v $VmlinuxLocation";
	system "$RamDumpParseBatName -a $DumpFilesLocation -v $VmlinuxLocation -o $DumpFilesLocation";
	print "Done!!...\n";
	print "Press Enter key to exit..."; <STDIN>;
}
sub go_T32 {	
	#----------------------------------------------------------------
	chdir "$RamDumpParserBatLocation" or die "error!!!\n";
	system "$launchT32BatName";
}
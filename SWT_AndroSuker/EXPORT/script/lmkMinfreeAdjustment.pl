#!/usr/bin/perl
#create by SUKER : 2013.05.28
#use lmkkillerminfree modified

use strict;

my $magnitude = 2;		#==> x5, x10, x15 ...
my $currentadj = "";
my $currentMinFree = "";
my $renewMinFreeValues = "";
my $lmkAdjFileName = "lmkAdj.tmp";
my $lmkMinFreeFileName = "lmkMinFree.tmp";
my @lmkMinFreelistOld;
my @lmkMinFreelistNew;

if ($#ARGV == 0)	{
	$magnitude = $ARGV[0];
	if ($magnitude >= 1500){
		print "magnitude percentage is too large";
		exit;
	} elsif ($magnitude <= 5)	{
		print "magnitude percentage is too small";
		exit;
	}
	print "=================================================================\n";
	print "****       you have to change your device to rooting!        ****\n";
	print "=================================================================\n";
	print "  doing...\n";
	print "  please wait...\n";
	print "=================================================================\n";
	&gogoLmkGetValues;
	&gogoLmkOpenFileAndView;
	&gogoLmkMinFreeParsing;
	&gogoLmkMinFreeWrite;
	&gogoLmkTmpFileRemove;
} else {
	print "fuck... \n";
	print "argument check please!!!...\n";
	exec("pause > nul");
}

sub gogoLmkGetValues {
	system "adb shell cat /sys/module/lowmemorykiller/parameters/adj > $lmkAdjFileName";
	sleep(1);
	system "adb shell cat /sys/module/lowmemorykiller/parameters/minfree > $lmkMinFreeFileName";
	sleep(1);
}

sub gogoLmkOpenFileAndView {
	if ( ! open LMKADJ, $lmkAdjFileName )	{
		die "Cannot open temp file: $!";
	}
	if ( ! open LMKMINFREE, $lmkMinFreeFileName )	{
		die "Cannot open temp file: $!";
	}
	$currentadj = <LMKADJ>;
	$currentMinFree = <LMKMINFREE>;
	print "----------------------------------------------------------------\n";
	print "your device lmk-adj     : $currentadj";
	print "your device lmk-minfree : $currentMinFree";
	print "----------------------------------------------------------------\n";
	print "read success!!\n";
	print "----------------------------------------------------------------\n";
	close LMKADJ;
	close LMKMINFREE;
}

sub gogoLmkTmpFileRemove {
	unlink $lmkAdjFileName;
	unlink $lmkMinFreeFileName;
}

sub gogoLmkMinFreeParsing {
	@lmkMinFreelistOld = split /,/,$currentMinFree;
	my $elements = 0;
	foreach $elements(@lmkMinFreelistOld)	{
		push @lmkMinFreelistNew, int($elements*($magnitude/100));
	}
		
	$renewMinFreeValues = join ",", @lmkMinFreelistNew;
	print "new low memory killer minfree value : $renewMinFreeValues\n\n";
}

sub gogoLmkMinFreeWrite {
	my $yesORno;
	print "Above value is right?(y/n)";
	chomp($yesORno = <STDIN>);
	
	if ($yesORno eq "y" || $yesORno eq "Y")
	{
		print "-------------------------------------------------\n";
		print "--        Processing...                        --\n";
		system "adb shell \"echo $renewMinFreeValues > /sys/module/lowmemorykiller/parameters/minfree\"";
		sleep(1);		
		print "--        Done...                              --\n";
		print "-------------------------------------------------\n";
		print "--     Below values are changed things.        --\n";
		&gogoLmkGetValues;
		&gogoLmkOpenFileAndView;
	}
	else
	{
		print "-------------------------------------------------\n";
		print "--        Cancel minfree modified !!           --\n";
		print "--        Good Bye!                            --\n";
		print "-------------------------------------------------\n";
	}
}
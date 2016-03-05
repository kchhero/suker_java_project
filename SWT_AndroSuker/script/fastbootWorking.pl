#!/usr/bin/perl

#arg0 ==> fastbootpath
#arg1 ==> imagespath
#arg2 ==> model info
#arg3 ==> order number
#arg3 ==> extArg0
#arg4 ==> extArg1
#...

$modelInfoStr = "";
$imagesPath = "";
$fastbootExePath = "";
$orderNumber = "";
$extArg0 = "";
$extArg1 = "";

if ($#ARGV > 0)	{
	$fastbootExePath = $ARGV[0];
	$imagesPath = $ARGV[1];
	$modelInfoStr = $ARGV[2];
	$orderNumber = $ARGV[3];
	$extArg0 = $ARGV[4];
	$extArg1 = $ARGV[5];
	
	&go_launch;
} else {
	print "fuck... \n";
	print "processing fail!!!...\n";
	exec("pause > nul");
}

sub go_launch {	
	#----------------------------------------------------------------
	#-----------------------msm8930 feature--------------------------
	#----------------------------------------------------------------
	if ($modelInfoStr eq "msm8930")	{
		if ($orderNumber eq "1")	{
			&emmc_appsboot_down;
			&rpm_down;
			&tz_down;
			&sbl3_down;
			&sbl2_down;
			&sbl1_down;
			&NON_HLOS_down;
			&boot_down;
			&system_down;
			&userdata_down;
			&persist_down;
			&recovery_down;
			&cache_down;
			#&tombstones_down;
		} 
		elsif ($orderNumber eq "2")	{	&emmc_appsboot_down;	}
		elsif ($orderNumber eq "3")	{	&boot_down;		}
		elsif ($orderNumber eq "4")	{	&system_down;		}
		elsif ($orderNumber eq "5")	{	&recovery_down;		}
		elsif ($orderNumber eq "6")	{	&NON_HLOS_down;		}
		elsif ($orderNumber eq "7")	{
			&rpm_down;
			&tz_down;
			&sbl3_down;
			&sbl2_down;
			&sbl1_down;
		}
		elsif ($orderNumber eq "8")	{	&erase_partition;		}
		elsif ($orderNumber eq "90")	{	&erase_userdata_and_cache;	}
		elsif ($orderNumber eq "100")	{	&reboot_device;			}
	
		print "Done... \n";
		print "press any key to finish...\n";
		exec("pause > nul");
	}
	#----------------------------------------------------------------
	else {
		print "fuck... \n";
		print "does not select model info !!...\n";
		exec("pause > nul");
	}
} 

sub emmc_appsboot_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash aboot $imagesPath/emmc_appsboot.mbn";
}
sub rpm_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash rpm $imagesPath/rpm.mbn";
}
sub tz_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash tz $imagesPath/tz.mbn";
}
sub sbl3_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash sbl3 $imagesPath/sbl3.mbn";
}
sub sbl2_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash sbl2 $imagesPath/sbl2.mbn";
}
sub sbl1_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash sbl1 $imagesPath/sbl1.mbn";
}
sub NON_HLOS_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash modem $imagesPath/NON-HLOS.bin";
}
sub boot_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash boot $imagesPath/boot.img";
}
sub system_down {
	print "Please wait... about 4 min \n\n";
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath erase system";
	system "$fastbootExePath flash -S 250M system $imagesPath/system.img";
}
sub userdata_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash userdata $imagesPath/userdata.img";
}
sub persist_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash persist $imagesPath/persist.img";
}
sub recovery_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash recovery $imagesPath/recovery.img";
}
sub cache_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash cache $imagesPath/cache.img";
}
sub tombstones_down {
	system "ping -n 2 127.0.0.1 >NUL";
	system "$fastbootExePath flash tombstones $imagesPath/tombstones.img.ext4";
}
sub erase_partition {
	system "ping -n 2 127.0.0.1 >NUL";	
	system "$fastbootExePath erase $extArg0";
}
sub erase_userdata_and_cache {
	system "$fastbootExePath -w";	
}

sub reboot_device {
	system "$fastbootExePath reboot";
}	

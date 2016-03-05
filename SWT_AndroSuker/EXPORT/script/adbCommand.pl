if ($ARGV[0] eq "RestartServer")	{
	if (!defined($kidpid = fork()))	{
		die "cannot fork: $!";
	} elsif($kidpid == 0)	{
		exec ("adb kill-server");
	} else	{
		waitpid($kidpid, 0);
		exec("adb start-server");
	}
}
 else {
	print "devices connecting problem...\n";
}

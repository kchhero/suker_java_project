#!/usr/bin/perl
use warnings;
use strict;
use Tk;

$|=1;

my $count = 0;
my $loop  = 0;
my $mw    = new MainWindow();

$mw->Label( -textvariable => \$count )->pack;

$mw->Button( -text => 'Start', -command => \&long_job )
  ->pack( -side => 'left' );

$mw->Button( -text => 'Stop', -command => sub { $loop = 0; print "$cou
+nt\n"; } )
  ->pack( -side => 'left' );

MainLoop();




sub long_job {

my $var;
my $timer = $mw->repeat(100, sub{$var++} ); 

   $loop =1;       
   while($loop){
          $loop++;
          $mw->waitVariable(\$var);
          $count++;
         }
  DoOneEvent();  #must call or will block gui
}
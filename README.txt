This is the dataset for the AI competition at the AMS annual meeting in
New Orleans, 2008. The competition is conducted by the Artificial
Intelligence Committee of the AMS and is sponsored by Weather Decision
Technologies of Norman, OK (http://www.wdtinc.com/).


Contents of zip file
************************
Download aicompetition.zip from
   http://cimms.ou.edu/~lakshman/aicompetition.zip
and extract the file (double-click on the file if you are using Windows XP).
Type:  unzip aicompetition.zip if you are using Linux

The zip archive contains these files:

   File              What it is                      Windows Programs for this file
   README.txt        This document                   Wordpad, Word, Web browser
   training.csv      Training Dataset (labeled)      Wordpad, Excel, Open Office
   testing.csv       Test Dataset                         ditto

Optional files (not needed for competition, but provides context).
These describe the project for which the dataset was created and
the decision tree used in that project:
   eric_guillot_paper.doc    Describes dataset          Word, Open Office
   eric_guillot_slides.ppt   Describes dataset          Powerpoint, Open Office
   StormType.java            Decision tree used         Wordpad, Eclipse(Java IDE)
   *.class                   Compiled Java byte code    Java Runtime (JRE)
   *.out                     Output of decision tree    Wordpad



Description of dataset
*************************
This dataset is the result of manual identification of storm-types.
The task is to devise an automated algorithm to classify storms based
on cluster attributes. For what the categories and attributes mean, please
see the Microsoft Word document in this zip file. The dataset was created
by Eric Guillot, so please be sure to reference his article (at the AMS
annual meeting in New Orleans, 2008) if you use this dataset.

The classifications (the last column in the training csv) are:
  0        :  NotSevere
  1        :  IsolatedSupercell
  2        :  ConvectiveLine
  4        :  PulseStorm

In the csv files, each storm cluster gets a line. The first 22 columns
are the attributes of the cluster and the 23rd column is the storm type.
In the testing csv file, the 23rd column has been removed. The 23 columns
in the csv files are listed below. In all cases, -99900 refers to missing
data.  Other than this special value, assume that the typical range of the
parameters is what you see in the training file.
  0: AspectRatio dimensionless (*) An ellipse is fitted to the storm. This is the ratio of the length of the major axis to the length of the minor axis of the fitted ellipse.
  1: ConvectiveArea km^2           Area of the storm that is convective 
  2: LatRadius km                  Extent of the storm in the north-south direction
  3: LatitudeOfCentroid Degrees    Location of storm's centroid
  4: LifetimeMESH mm               Maximum expected hail size of the storm over its entire past history
  5: LifetimePOSH dimensionless    Peak probability of severe hail of the storm over its entire past history
  6: LonRadius km                  Extent of the storm in the east-west direction
  7: LongitudeOfCentroid Degrees   Location of the storm's centroid
  8: LowLvlShear s^-1 (*)          Shear closest to the ground as measured by radar
  9: MESH mm                       Maximum expected hail size from storm
 10: MaxRef dBZ                    Maximum reflectivity observed in storm
 11: MaxVIL kg/m^2 (*)             Maximum vertical integrated liquid in storm
 12: MeanRef dBZ (*)               Mean reflectivity within storm
 13: MotionEast MetersPerSecond    Speed of storm in easterly direction
 14: MotionSouth MetersPerSecond   Speed of storm in southerly direction
 15: OrientationToDueNorth degrees (*)   orientation of major axis of ellipse to due north.  A value of 90 indicates a storm that is oriented east-west. The more circular a storm is (see aspect ratio), the less reliable this measure is.
 16: POSH dimensionless            Peak probability of severe hail in storm
 17: Rot120 s^-1                   Maximum azimuthal shear observed in storm over the past 120 minutes
 18: Rot30 s^-1                    Maximum azimuthal shear observed in storm over the past 30 minutes
 19: RowName dimensionless         Storm id
 20: Size km^2 (*)                 Storm size
 21: Speed MetersPerSecond (*)     Speed of storm
 22: StormType dimensionless    [This column has been removed from the testing dataset since it is what needs to be predicted]
 
You probably will not use all of these attributes: centroid location and
RowName come to mind as ones that probably should not be used. But they have
been left in the dataset in case you have an innovative way of using them!
The starred(*) attributes are the ones that were used in the decision tree
referenced in the paper.

Description of Competition
*******************************
Your task is to devise an algorithm that will be able to correctly
classify storms based on these attributes. A testing dataset has been
provided to you. You should process the dataset with your algorithm and
provide the categories (one per line). You should also write a paper
describing the technique you used and submit the paper to the AI conference
at the AMS annual meeting in New Orleans (2008). The team with the best
performance on the test dataset wins. Submitting an implementation of your
algorithm (in Matlab/C/Java/Fortran/etc.) is recommended, but not required.
The final decision on the ranking of submitted algorithms will be made by the
AI committee using the True Skill Statistic
(see http://www.bom.gov.au/bmrc/wefor/staff/eee/verif/verif_web_page.html for
a definition of TSS for multi-category forecasts) and can not be appealed.

The prizes, subject to receiving enough submissions, are as follows:
   First:  $500
   Second OR Best student entry (at the discretion of the committee): $300
   Third  OR second, if student entry received second prize:  $200


Deadlines:
*******************************************************
(1) Participating teams should submit your results on the test data set by Dec. 15, 2007.
Simply send them to Beth Ebert and me via email (zip the file up first).
At this time, you should submit a formal abstract to the AMS (and pay the preprint fee).

(2) We'll send you the skill score on the test data set by Jan. 10, 2008.

(3) The final manuscript, incorporating those results should be submitted on the AMS website by Jan. 17, 2008.
The AMS website also allows you to upload your presentation slides. I encourage you to do so at the time you
are uploading the manuscript.


Description of example algorithm and performance measure
********************************************************
For convenience (and as proof that the dataset is tractable), a simple
automated algorithm that is capable of classifying storms is provided.
To try out this algorithm, run on a machine with Java installed:
        java Stormtype training.csv training.out
        java Stormtype testing.csv testing.out
To compile the program, use javac:   javac StormType.java

Here are the statistics on the training and testing set achieved by the
sample decision tree:
On the training set:
       Got->    0     1     2     3     4   Accuracy
Expected   0:   464   2     2     0     58   88.2
Expected   1:   5     167   16    0     34   75.2
Expected   2:   8     47    122   0     31   58.7
Expected   3:   0     0     0     0     0    
Expected   4:   50    13    15    0     322   80.5
Accuracy        88    72.9  78.7       72.4   
True Skill Score = 0.71

On the testing set (Note that you can not reproduce this because in your copy,
the actual StormType column has been removed).
       Got->    0     1     2     3     4   Accuracy
Expected   0:   498   0     3     0     68   87.5
Expected   1:   2     82   14     0     33   62.6
Expected   2:   4     28   41     0     22   43.2
Expected   3:   0     0     0     0     0    
Expected   4:   57    24   25     0     168   61.3
Accuracy        89  61.2  49.4         57.7
True Skill Score = 0.58


Contact Information
**************************
If you have any questions or suggestions, please feel free to
contact one of the following:
Valliappa Lakshmanan    lakshman@ou.edu
Beth Ebert              E.Ebert@bom.gov.au
Sue Ellen Haupt         seh19@psu.edu



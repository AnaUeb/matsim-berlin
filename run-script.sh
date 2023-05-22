#!/bin/bash --login
#$ -cwd
#$ -N matsim-example
#$ -m be
#$ -M a.ueberhorst@campus.tu-berlin.de
#$ -j y
#$ -o job-log.log
#$ -l h_rt=80000
#$ -l mem_free=4G
#$ -pe mp 2

# make sure java is present
module add java/17

# start matsim with classic run script
java -cp ./app.jar -Xmx6G org.matsim.project.RunMatsim ./input/config.xml --config:controler.runId test-run --config:controler.outputDirectory ./output

# start matsim application
java -jar ./app.jar -Xmx6G run --config .\scenarios\input\test.config.xml --config:controler.outputDirectory ./output

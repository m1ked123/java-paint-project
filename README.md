#Version 0.4.1 [9/8/15]

##Application Summary
This is a simple painting application in the same vein as Microsoft Paint. It has little functionality, but allows for some of the most basic functionality including the ability to pain on a blank canvas in various colors and sizes.

##What's New
	
###bug fixes
* No major bug fixes this cycle

###features
* Can no longer resize the paint brush larger than 10px

		
###code
* All paint code has been consolidated into one method
		
##Known Bugs
* The application can lag after drawing a really long line.
* Resizing the frame does not resize the drawing area; resizing is not enabled for this reason

##Next Steps
* fix resizing issue by resizing the canvas when the frame is resized
* ability to erase entities
* code consolidation (a never-ending job)

##Version Scheme
For those interested, the version numbering scheme in the code is as follows: major.minor.revision. This means that a version of 0.1.1 signifies a program that is in it's first stage of development (alpha or beta), that there has been one minor build or push to master, and there has been one minor revision in this particular push*.
	
* For code that had been pushed to master before the consistent numbering had been used, the minor number may be a little lower than one might expect.

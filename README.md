# Runtime Call Tree [![Build Status](https://travis-ci.org/elmuerte/rtcalltree.svg?branch=master)](https://travis-ci.org/elmuerte/rtcalltree)

Provides functionality to record method call trees at runtime. This can help you get insight in how your program executes.

# Project Goal

The goal of this project is to provide insight in the control flow in an application based on the method which are called. 
When programs increase in size and complexity it becomes more difficult to understand what parts of the system are executed
in what order. This is especially the case when programs are not well defined and developed.

Profilers and monitors (like [JAMon](http://jamonapi.sourceforge.net/)) can give you insight in call trees, but their focus 
lies on aggregation and performance metrics. The order of execution is lost.  


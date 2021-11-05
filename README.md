# eclipse-dance-plugin

If you have ever developed an OSGi plugin you may be familiar with the Eclipse Dance:

* Reload target platform
* Refresh projects
* Clean all projects
* Add all bundles from target platform into Run/Debug Configuration
* And sometimes, delete the work directory!

This plugin replaces all of that with a shiny button.

All items can be enabled or disabled.

## Installation

See https://orionhealth.github.io/eclipse-dance-plugin/

## For Developers

TODO

### Building
In Eclipse, right click the top level plugin project, choose `Export > Deployable features`, set the location as the path to the `update-site`. This will mean the update site is properly linked up with the exported feature jar.

In Eclipse, right click the top level feature project, choose `Export > Deployable plug-ins and fragments`, set the location as the path to the `update-site`. This will mean the update site is properly linked up with the exported plugin jar.


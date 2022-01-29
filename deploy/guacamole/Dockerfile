# -*- coding: utf-8 -*-
#
# This file is covered by the LICENSING file in the root of this project.
#

### Dockerfile for guacamole
### based on guacamole official image

FROM guacamole/guacamole:1.4.0

USER root

RUN mkdir -p /opt/openhackathon/guacamole
ADD . /opt/openhackathon/guacamole/

ENV GUACAMOLE_HOME /opt/openhackathon/guacamole

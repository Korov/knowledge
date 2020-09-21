#! /bin/bash

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/agent.conf &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf
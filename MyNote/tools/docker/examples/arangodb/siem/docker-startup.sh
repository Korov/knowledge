#!/bin/sh

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/agent.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--log.file /var/lib/arangodb3/agency.log \
--log.force-direct false \
--server.endpoint tcp://0.0.0.0:8531 \
--agency.my-address tcp://127.0.0.1:8531 \
--server.authentication false \
--agency.activate true \
--agency.size 1 \
--agency.endpoint tcp://127.0.0.1:8531 \
--agency.supervision true \
--database.directory /var/lib/arangodb3/agency &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--log.file /var/lib/arangodb3/dbserver1.log \
--log.force-direct false \
--server.authentication=false \
--server.endpoint tcp://0.0.0.0:8526 \
--cluster.my-address tcp://127.0.0.1:8526 \
--cluster.my-role DBSERVER \
--cluster.agency-endpoint tcp://127.0.0.1:8531 \
--database.directory /var/lib/arangodb3/dbserver1 &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--log.file /var/lib/arangodb3/dbserver2.log \
--log.force-direct false \
--server.authentication=false \
--server.endpoint tcp://0.0.0.0:8527 \
--cluster.my-address tcp://127.0.0.1:8527 \
--cluster.my-role DBSERVER \
--cluster.agency-endpoint tcp://127.0.0.1:8531 \
--database.directory /var/lib/arangodb3/dbserver2 &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--log.file /var/lib/arangodb3/coordinator.log \
--log.force-direct false \
--server.endpoint tcp://0.0.0.0:8529 \
--cluster.my-address tcp://127.0.0.1:8529 \
--server.authentication false \
--cluster.my-role COORDINATOR \
--cluster.agency-endpoint tcp://127.0.0.1:8531 \
--database.directory /var/lib/arangodb3/coordinator

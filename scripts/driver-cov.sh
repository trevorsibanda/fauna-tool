#!/usr/bin/bash

mkdir drivers
cd drivers
git clone https://github.com/fauna/faunadb-jvm
git clone https://github.com/fauna/faunadb-go
git clone https://github.com/fauna/faunadb-python
git clone https://github.com/fauna/faunadb-js
git clone https://github.com/fauna/faunadb-ruby

export FAUNA_ROOT_KEY=secret FAUNA_SCHEME=http FAUNA_DOMAIN=127.0.0.1 FAUNA_PORT=8443 FAUNA_ENDPOINT=http://127.0.0.1:8443/
for D in *; do [ -d "${D}" ] && cd "${D}"; make test & cd ..; done

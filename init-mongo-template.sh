mongo -- "$MONGO_INITDB_DATABASE" <<EOF
    var rootUser = '$MONGO_INITDB_ROOT_USERNAME';
    var rootPassword = '$MONGO_INITDB_ROOT_PASSWORD';
    var admin = db.getSiblingDB('admin');
    admin.auth(rootUser, rootPassword);

    var user = 'sample';
    var passwd = 'sample';
    use gigety;
    db.createUser({user: user, pwd: passwd, roles: [ { "role" : "readWrite", "db" : "gigety" } ]});
EOF

# THis is a sample file that should be replaced with init-mongo.sh - explanation:

# TODO: init-mongo shell script appears to require hardcoded user password.
# Not sure yet. For some reasopn the web application does not connect if
# if the user was created via environment variable. Hardcoded however works
# Very strange because the user is created in mongo via env variable.
# But web app is simply failing authentication if created via variable.
# Spent too much time looking at this. I am going to upload a sample file
# called init-mongo-sample.sh for reference
# Very strange because the user is created in mongo via env variable.
# But web app is simply failing authentication if created via variable.
# Spent too much time looking at this. I am going to upload a sample file
# called init-mongo-sample.sh for reference..

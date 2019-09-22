# Gigety  

## mysql  
### Docker  
```
docker start samo-mysql
docker network connect samo-mysql gigety
```  

## redis
### Docker 
```
docker run -d --name gig-redis -p 6379:6379 --network gigety redis 
```  

## gigety-user-registration  
### Docker  
```
docker container run --network gigety --name gig-ur -p 8083:8084 -e SMS_GMAIL_NAME=$SMS_GMAIL_NAME -e SMS_GMAIL_PW=$SMS_GMAIL_PW -d gig-ur
```  


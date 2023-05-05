REPOSITORY=//home/jms/cp-server/
TARGET=$REPOSITORY/build/libs
PROJECT_NAME=sohot

cd $REPOSITORY

echo "> Git checkout master"

git checkout master

echo "> Git Pull origin master"

git pull origin master

echo "> 프로젝트 Build 시작"

chmod +x gradlew

./gradlew clean build -x test

echo "> target 디렉토리로 이동"

cd "$TARGET"

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 새 애플리케이션 배포"

JAR_NAME=$(ls -tr $TARGET | grep jar | grep -v plain  | tail -n 1)

echo "> JAR Name: $JAR_NAME"

nohup java -jar \
       -Dspring.profiles.active=dev \
       $TARGET/$JAR_NAME > /dev/null 2>&1 &
angular.module('springChat.controllers', ['toaster'])
    .controller('ChatController', ['$scope', '$location', '$interval', 'toaster', 'ChatSocket', function($scope, $location, $interval, toaster, chatSocket) {
          
        var typing = undefined;
        $scope.username     = '';
        $scope.sendTo       = 'everyone';
        $scope.participants = [];
        $scope.messages     = [];
        $scope.newMessage   = ''; 
          
        $scope.sendMessage = function() {
            chatSocket.send(
                "/app/chat.message", 
                {}, 
                JSON.stringify({
                    message: $scope.newMessage,
                    username: $scope.username
                }));
            $scope.newMessage = '';
        };
        
        $scope.startTyping = function() {
            typing = $interval(function() {
                $scope.stopTyping();
            }, 500);
            
            chatSocket.send(
                "/topic/chat.typing", 
                {}, 
                JSON.stringify({
                    username: $scope.username, 
                    typing: true
                })
            );
        };
        
        $scope.stopTyping = function() {
            if (angular.isDefined(typing)) {
                $interval.cancel(typing);
                typing = undefined;
                chatSocket.send(
                    "/topic/chat.typing", 
                    {}, 
                    JSON.stringify({
                        username: $scope.username, 
                        typing: false
                    })
                );
            }
        };
        
        var initStompClient = function() {
            chatSocket.init('/ws');
            
            chatSocket.connect(function(frame) {
                chatSocket.subscribe("/app/chat.participants", function(message) {
                    $scope.participants = JSON.parse(message.body);
                });
                chatSocket.subscribe("/app/chat.login", function(message) {
                    $scope.participants.unshift({
                        username: JSON.parse(message.body).username, 
                        typing : false});
                });
                chatSocket.subscribe("/app/chat.logout", function(message) {
                    var username = JSON.parse(message.body).username;
                    for(var index in $scope.participants) {
                        if($scope.participants[index].username == username) {
                            $scope.participants.splice(index, 1);
                        }
                    }
                });
                chatSocket.subscribe("/app/chat.typing", function(message) {
                    var parsed = JSON.parse(message.body);
                    if(parsed.username == $scope.username) return;
                    for(var index in $scope.participants) {
                        var participant = $scope.participants[index];
                          
                        if(participant.username == parsed.username) {
                            $scope.participants[index].typing = parsed.typing;
                        }
                      } 
                });
                chatSocket.subscribe("/app/chat.message", function(message) {
                    $scope.messages.unshift(JSON.parse(message.body));
                });
                  
            }, function(error) {
                toaster.pop('error', 'Error', 'Connection error ' + error);
                
            });
        };
        
        $scope.initUsername = function(){
            $scope.username = document.getElementById("username").value;
        }
          
        $scope.showName = function(sendTo){
            if(sendTo === 'everyone'){
                return "所有人;"
            }
            return sendTo;
        }
        
        initStompClient();
    }
]);
    
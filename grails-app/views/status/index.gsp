<html>
<head>
    <meta name="layout" content="main"/>
    <title>What Are You Doing?</title>
</head>
<body>
    <g:set var="displayName"><twitter:renderCurrentUserName /></g:set>

    <g:render template="/navbar" />

    <div class="pageBody">
        <div class="column sideboard">
            <div id="profile" class="panel">
                <div>
                    <gravatar:image email="${person.email ?: person.displayName}" title="${person.displayName}"
                                    defaultGravatarUrl="retro" />
                    <h2>${displayName}</h2>
                </div>
                <div id="userStats">
                    <dl>
                        <dt><g:message code="status.updateCount.label" /></dt><dd>${totalStatusCount}</dd>
                        <dt><g:message code="status.followingCount.label" /></dt><dd>${following.size()}</dd>
                        <dt><g:message code="status.followersCount.label" /></dt><dd>${followers.size()}</dd>
                    </dl>
                </div>
            </div>
            <div id="following" class="panel">
                <h2><g:message code="status.followingList.label" /></h2>
                <g:render template="/status/peopleList" model="[people: following]" />
            </div>
            <div id="followers" class="panel">
                <h2><g:message code="status.followersList.label" /></h2>
                <g:render template="/status/peopleList" model="[people: followers]" />
            </div>
            <div id="otherUsers" class="panel">
                <h2><g:message code="status.otherUsersList.label" /></h2>
                <g:render template="/status/peopleList" model="[people: otherUsers]" />
            </div>
        </div>
        <div class="column mainboard">
            <h1><g:message code="status.greeting" args="${displayName}" /></h1>
            <div class="updateStatusForm">
                <g:form name="updateStatusForm">
                    <g:textField name="message" value="" placeholder="${g.message(code: 'status.placeholder')}" />
                </g:form>
            </div>
            <div id="messages">
                <twitter:renderMessages messages="${statusMessages}"/>
            </div>
        </div>
    </div>

    <asset:javascript src="spring-websocket" />
    <script type="text/javascript">
        $(function() {
            var socket = new SockJS("${createLink(uri: "/stomp")}");
            var client = Stomp.over(socket);

            function sendStatusUpdate(message) {
                if (message) {
                    client.send("/app/updateStatus", {}, message);
                }
            }
            
            function receiveStatusUpdate(message) {
                $(message.body).prependTo("#messages").hide().slideDown();
            }

            $("#updateStatusForm").submit(function() {
                var $message = $("#message");
                sendStatusUpdate($.trim($message.val()));
                $message.val("");
                return false;
            });

            client.connect({}, function() {
                client.subscribe("/user/queue/timeline", receiveStatusUpdate);
            });
        });
    </script>
</body>
</html>

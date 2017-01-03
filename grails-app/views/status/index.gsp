<html>
<head>
    <meta name="layout" content="main"/>
    <title>What Are You Doing?</title>
</head>
<body>
    <div class="nav" role="navigation">
        <ul>
            <li><a class="home" href="${createLink(uri: '/status')}"><g:message code="default.home.label"/></a></li>
            <li><g:link controller="person">Users</g:link></li>
            <li><g:link controller='logout'>Logout</g:link></li>
        </ul>
    </div>
    <div class="pageBody">
        <h1>Hello <twitter:renderCurrentUserName/>. What Are You Doing?</h1>
        <div class="updateStatusForm">
            <g:formRemote url="[action: 'updateStatus']" update="messages" name="updateStatusForm"
                          onSuccess="document.getElementById('message').value=''">
                <g:textArea name="message" value=""/><br/>
                <g:submitButton value="Update Status" name="updateStatus" id="update_status_button"/>
            </g:formRemote>
        </div>
        <div id="messages">
            <twitter:renderMessages messages="${statusMessages}"/>
        </div>
    </div>

    <asset:javascript src="spring-websocket" />
    <script type="text/javascript">
        $(function() {
            var socket = new SockJS("${createLink(uri: '/stomp')}");
            var client = Stomp.over(socket);

            client.connect({}, function() {
                client.subscribe('/user/queue/timeline', function(message) {
                    $('#messages').prepend(message.body);
                })
            });
        });
    </script>
</body>
</html>

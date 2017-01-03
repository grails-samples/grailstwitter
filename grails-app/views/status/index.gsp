<%@ page import="org.grails.twitter.Status" %>

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
        <div class="column sideboard">
            <div id="profile" class="panel">
                <div>
                    <gravatar:image email="${person.email ?: person.displayName}" defaultGravatarUrl="retro" />
                    <h2><twitter:renderCurrentUserName/></h2>
                </div>
                <div id="userStats">
                    <dl>
                        <dt>Status Updates</dt><dd>${tweetCount}</dd>
                        <dt>Following</dt><dd>${following.size()}</dd>
                        <dt>Followers</dt><dd>${followers.size()}</dd>
                    </dl>
                </div>
            </div>
            <div id="following" class="panel">
                <h2>Following</h2>
                <g:render template="/status/peopleList" model="[people: following]" />
            </div>
            <div id="followers" class="panel">
                <h2>Followers</h2>
                <g:render template="/status/peopleList" model="[people: followers]" />
            </div>
            <div id="otherUsers" class="panel">
                <h2>Other People</h2>
                <g:render template="/status/peopleList" model="[people: otherUsers]" />
            </div>
        </div>
        <div class="column mainboard">
            <h1>Hello, <twitter:renderCurrentUserName/>...</h1>
            <div class="updateStatusForm">
                <g:formRemote url="[action: 'updateStatus']" update="messages" name="updateStatusForm"
                              onSuccess="document.getElementById('message').value=''">
                    <g:textField name="message" value="" placeholder="What are you doing?" /><br/>
                </g:formRemote>
            </div>
            <div id="messages">
                <twitter:renderMessages messages="${statusMessages}"/>
            </div>
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

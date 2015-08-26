<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#show-person" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/status')}"><g:message code="default.home.label"/></a></li>
                <li><g:link controller="person">Users</g:link></li>
                <li><g:link controller='logout'>Logout</g:link></li>
            </ul>
        </div>

        <div class="pageBody">
            <h1>${person.displayName}</h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <span class="property-label">User Name: </span>
            <span class="property-value">${person.userName}</span>

            <h1>Following</h1>
            <g:if test="${!person.followed}">none</g:if>
            <f:table collection="${person.followed}" properties="['displayName', 'userName']"/>

            <h1>Latest Messages by ${person.displayName}</h1>
            <g:if test="${!messages}">none</g:if>
            <twitter:renderMessages messages="${messages}"/>
        </div>
    </body>
</html>

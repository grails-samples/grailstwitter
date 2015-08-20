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
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
            </ul>
        </div>
        <div class="pageBody">
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <ol class="property-list person">
                <li class="fieldcontain">
                    <span class="property-label">User Name</span>
                    <div class="property-value" aria-labelledby="userName-label">${person.userName}</div>
                </li>
                <li class="fieldcontain">
                    <span class="property-label">Display Name</span>
                    <div class="property-value" aria-labelledby="userName-label">${person.displayName}</div>
                </li>
            </ol>

            <h1>Following</h1>
            <g:if test="${!person.followed}">none</g:if>
            <f:table collection="${person.followed}" properties="['displayName', 'userName']"/>

            <h1>Latest Messages by ${person.userName}</h1>
            <g:if test="${!messages}">none</g:if>
            <twitter:renderMessages messages="${messages}"/>
        </div>
    </body>
</html>

<div class="statusMessage" id="message_${statusMessage.id}">
    <g:set var="author" value="${statusMessage.author}" />
    <gravatar:image email="${author.email ?: author.displayName}"
                    title="${author.displayName}"
                    defaultGravatarUrl="retro" />
    <div>
        <div class="byline">
            <span class="author">${author.displayName}</span>
            <span class="handle">@${author.userName}</span> •
            <span class="created">
                <g:formatDate format="yyyy MMM d HH:mm:ss" date="${statusMessage.dateCreated}" />
            </span>
        </div>
        <div>
            <span class="statusMessage">${statusMessage.message}</span><br/>
        </div>
    </div>
</div>

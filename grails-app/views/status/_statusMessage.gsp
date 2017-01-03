<div class="statusMessage" id="message_${statusMessage.id}">
    <gravatar:image email="${statusMessage.author.email ?: statusMessage.author.displayName}"
                    defaultGravatarUrl="retro" />
    <div>
        <div class="byline">
            <span class="author">${statusMessage.author.displayName}</span>
            <span class="handle">@${statusMessage.author.userName}</span> •
            <span class="created">
                <g:formatDate format="yyyy MMM d HH:mm:ss" date="${statusMessage.dateCreated}" />
            </span>
        </div>
        <div>
            <span class="statusMessage">${statusMessage.message}</span><br/>
        </div>
    </div>
</div>

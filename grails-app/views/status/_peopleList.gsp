<ul class="personList">
    <g:each in="${people}" var="p">
        <li>
            <gravatar:image email="${p.email ?: p.displayName}" title="${p.displayName}"
                            defaultGravatarUrl="retro" />
            ${p.displayName} <span class="byline"> @${p.userName}</span>
        </li>
    </g:each>
</ul>

<ul class="personList">
    <g:each in="${people}" var="p">
        <li>
            <gravatar:image email="${p.email ?: p.displayName}" defaultGravatarUrl="retro" />
            ${p.displayName}
        </li>
    </g:each>
</ul>

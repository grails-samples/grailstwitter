security = {
    authorize {
        authenticated "/status/**", "/updateStatus/**", "/users/**", "/unfollow/**", "/follow/**"
    }

    loginUrl '/login'
    logoutSuccessUrl '/'
    csrf false
}

users = {
    inMemory {
        newUser username: 'jeff', password: 'password', roles: ['USER']
        newUser username: 'graeme', password: 'password', roles: ['USER']
        newUser username: 'lari', password: 'password', roles: ['USER']
    }
}

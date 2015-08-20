class UrlMappings {

    static mappings = {
        "/login"(controller: 'auth', action: 'index')
        "/status"(controller: 'status', action: 'index')
        "/updateStatus"(controller: 'status', action: 'updateStatus')
        "/users"(controller: 'person', action: 'index')
        "/person"(controller: 'person', action: 'show')
        "/unfollow/$userToUnfollow"(controller: 'person', action: 'unfollow')
        "/follow/$userToFollow"(controller: 'person', action: 'follow')
        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}

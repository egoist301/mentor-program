import React from 'react';
import { Route } from "react-router-dom";
import NoPermission from "./NoPermission";


const PrivateAdminRoute = ({ component: Component, authenticated, currentUser, ...rest }) => (
    <Route
        {...rest}
        render={props =>
            authenticated && currentUser.role === 'ROLE_ADMIN' ? (
                <Component {...rest} {...props} />
            ) : (
                    <NoPermission />
                )
        }
    />
);

export default PrivateAdminRoute
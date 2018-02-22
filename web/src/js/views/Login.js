import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { login } from '../actions';

import TextField from '../components/TextField';
import Button from '../components/Button';

import '../../css/views/Login.scss';

export default class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: ''
        };

        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleButtonClick = this.handleButtonClick.bind(this);
    }

    handleEmailChange(event) {
        const target = event.target;

        this.setState({
            email: target.value
        });
    }

    handlePasswordChange(event) {
        const target = event.target;

        this.setState({
            password: target.value
        });
    }

    handleButtonClick(event) {
        const { email, password } = this.state;
        const { store } = this.context;

        fetch(`service/login?email=${email}&password=${password}`, {
            method: 'POST'
        }).then(result => result.json())
            .then(json => {
                store.dispatch(login(json));
            });
    }

    render() {
        const { email, password } = this.state;
        return (
            <div className="Login">
                <TextField label="email" value={email} onChange={this.handleEmailChange} />
                <TextField label="password" type={"password"} value={password} onChange={this.handlePasswordChange} />
                <Button onClick={this.handleButtonClick} text={"LOGIN"} />
            </div>
        );
    }
}

Login.contextTypes = {
    store: PropTypes.object
};
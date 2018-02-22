import React, { Component } from 'react';
import { connect } from 'react-redux';
import _ from 'lodash';

import Login from './Login';
import Main from './Main';

const LoginContainer =  ({ mode }) => {
    return (
        <div className="LoginContainer">
            { mode === 'login' ? <Login /> : <Main /> }
        </div>
    );
};

const mapStateToProps = (state) => ({
    mode: _.isEmpty(state.user) ? 'login' : 'app'
});

const mapDispatchToProps = (dispatch) => ({

});

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(LoginContainer);
import React, { Component } from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import app from './reducers';

import TopBar from './views/TopBar';
import LoginContainer from './views/LoginContainer';

import '../css/style.scss';

let store = createStore(app);

console.log(store.getState());

const unsubscribe = store.subscribe(() =>
    console.log(store.getState())
)

class App extends Component {
    render() {
        return (
            <Provider store={store}>
                <div>
                    <TopBar/>
                    <LoginContainer />
                </div>
            </Provider>
        );
    }
}

render(<App />, document.getElementById('app'));
import { combineReducers } from 'redux';
import {
    LOGIN,
    CONNECT_SQUARE,
    CONNECT_SHOPIFY,
} from './actions';

function user(state = {}, action) {
    switch (action.type) {
        case LOGIN:
            return action.user;
        case CONNECT_SQUARE:
            return Object.assign({}, state, {
                merchantId: action.merchantId
            });
        case CONNECT_SHOPIFY:
            return Object.assign({}, state, {
                shopName: action.shopName
            });
        default:
            return state;
    }
}

const app = combineReducers({
    user
});

export default app;
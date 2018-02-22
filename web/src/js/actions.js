function makeActionCreator(type, ...argNames) {
    return function (...args) {
        let action = { type }
        argNames.forEach((arg, index) => {
            action[argNames[index]] = args[index]
        })
        return action
    }
}

export const LOGIN = 'LOGIN';
export const CONNECT_SQUARE = 'CONNECT_SQUARE';
export const CONNECT_SHOPIFY = 'CONNECT_SHOPIFY';

export const login = makeActionCreator(LOGIN, 'user');
export const connectSquare = makeActionCreator(CONNECT_SQUARE, 'merchantId');
export const connectShopify = makeActionCreator(CONNECT_SHOPIFY, 'shopName');
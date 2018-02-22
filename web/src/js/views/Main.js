import React, { Component } from 'react';
import Tabs from '../components/Tabs';
import SquareApiConnect from "./SquareApiConnect";
import ShopifyApiConnect from "./ShopifyApiConnect";

export default class Main extends Component {
    render() {
        return (
            <div className="Main">
                <Tabs
                    tabs={[
                        {
                            title: 'Connect',
                            body: <div>
                                <SquareApiConnect />
                                <ShopifyApiConnect />
                            </div>
                        },
                        {
                            title: 'Logs',
                            body: <div>Logs!</div>
                        }
                    ]}
                />
            </div>
        );
    }
}
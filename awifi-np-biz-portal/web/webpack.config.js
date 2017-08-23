/**
 * 版权所有: 爱WiFi无线运营中心
 * 创建日期: 2017-04-12 14:44
 * 创建作者: haoxu
 * 文件名称: webpack.js
 * 版本: v1.0
 * 功能: xx
 * 修改记录: xx
 */
var path = require('path');
var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

// 本地开发环境配置项 dev
var publicPath = '/static/np-admin/';
//var proxyTarget = 'http://192.168.41.80'; // alpha数据
var proxyTarget = 'http://beta-toe-portal.51awifi.com'; // local数据
var scssLoader = 'css-loader?importLoaders=1!sass-loader!postcss-loader';

// 生产环境配置项 prod
if (process.env.NODE_ENV === 'production') {
    // publicPath = '/static/np-admin/';

    scssLoader = ExtractTextPlugin.extract({
        use: ['css-loader', 'sass-loader', 'postcss-loader'],
        fallback: 'vue-style-loader'
    });
}

module.exports = {
    entry: {
        'index': './static/index.js'
    },
    output: {
        path: path.resolve(__dirname, '/static/np-admin/'), // 生成文件目录
        publicPath: publicPath, // 静态文件引用路径
        filename: 'js/[name].js'
    },
    module: {
        rules: [
            {   test: /\.js$/,
                loader: "jsx-loader!babel-loader",
                exclude: /node_modules/
            },
            {
                test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
                loader: 'url-loader',
                options: {
                    limit: 10000, // 10KB
                    name: 'img/[name].[ext]?[hash]'
                }
            }, {
                test: /\.scss$/,
                loader: scssLoader
            },{
                test: /\.css$/,
                loader: scssLoader
            }
        ]
    },
    resolve: {

    },
    //devServer: {
    //    historyApiFallback: true,
    //    noInfo: true,
    //    proxy: {
    //        '/component': {
    //            //target: proxyTarget, //'http://192.168.41.80',
    //            target: 'http://192.168.41.80',
    //            secure: false
    //        }
    //    }
    //},
    performance: {
        hints: false
    },
    devtool: '#source-map'
};

if (process.env.NODE_ENV === 'production') {
    module.exports.devtool = '#source-map';
    // http://vue-loader.vuejs.org/en/workflow/production.html
    module.exports.plugins = (module.exports.plugins || []).concat([
        new webpack.DefinePlugin({
            'process.env': {
                NODE_ENV: '"production"'
            }
        }),
        new webpack.optimize.UglifyJsPlugin({
            sourceMap: true,
            compress: {
                warnings: false
            }
        }),
        new webpack.LoaderOptionsPlugin({
            minimize: true
        }),
        // 输出独立文件
        new ExtractTextPlugin({
            filename: 'css/[name].css',
            disable: false,
            allChunks: true
        }),
    ])
}

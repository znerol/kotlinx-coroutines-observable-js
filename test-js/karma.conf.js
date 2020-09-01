module.exports = function(config) {
    config.set({
        frameworks: [
            "mocha",
            "chai"
        ],
        plugins: [
            "karma-webpack",
        ],
        reporters: ["progress"],
        port: 9876,  // karma web server port
        colors: true,
        logLevel: config.LOG_INFO,
        autoWatch: false,
        // singleRun: false, // Karma captures browsers, runs the tests and exits
        concurrency: Infinity,
        files: [
            {
                pattern: "*.spec.js",
            }
        ],
        preprocessors: {
            "*.spec.js": ["webpack"],
        },
        webpack: {
        },

        webpackMiddleware: {
            stats: 'errors-only',
        },
    })
}


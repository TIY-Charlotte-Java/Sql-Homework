var gulp = require('gulp');
var sass = require('gulp-sass');

gulp.task('styles', function() {
    gulp.src('src/main/resources/sass/*.scss')
        .pipe(sass().on('error', sass.logError))
        .pipe(gulp.dest('public/styles/'))
});

gulp.task('js', function() {
    gulp.src('src/main/resources/angular/*.js')
        .pipe(gulp.dest('public/js/'));
    gulp.src('bower_components/angular/angular.js')
        .pipe(gulp.dest('public/js/'));
    gulp.src('bower_components/angular-resource/angular-resource.js')
        .pipe(gulp.dest('public/js/'));
});

gulp.task('angular-html', function() {
    gulp.src('src/main/resources/angular/*.html')
        .pipe(gulp.dest('public/js/'))
});

//Watch task
gulp.task('default',function() {
    gulp.watch('src/main/resources/sass/*.scss',['styles']);
    //gulp.watch('src/main/resources/angular/*.js',['js']);
    //gulp.watch('src/main/resources/angular/*.html',['angular-html']);
});
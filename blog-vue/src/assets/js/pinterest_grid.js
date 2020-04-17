/*
    Pinterest Grid Plugin
    Copyright 2014 Mediademons
    @author smm 16/04/2014

    usage:

     $(document).ready(function() {

        $('#blog-landing').pinterest_grid({
            no_columns: 4
        });

    });
*/
// eslint-disable-next-line no-shadow-restricted-names
;(function ($, window, document, undefined) {
  var pluginName = 'pinterest_grid'
  var defaults = {
    padding_x: 10,
    padding_y: 10,
    no_columns: 3,
    margin_bottom: 50,
    single_column_breakpoint: 700
  }
  var columns
  var $article
  // eslint-disable-next-line camelcase
  var article_width

  function Plugin (element, options) {
    this.element = element
    this.options = $.extend({}, defaults, options)
    this._defaults = defaults
    this._name = pluginName
    this.init()
  }

  Plugin.prototype.init = function () {
    var self = this
    // eslint-disable-next-line camelcase
    var resize_finish

    $(window).resize(function () {
      clearTimeout(resize_finish)
      // eslint-disable-next-line camelcase
      resize_finish = setTimeout(function () {
        self.make_layout_change(self)
      }, 11)
    })

    self.make_layout_change(self)

    setTimeout(function () {
      $(window).resize()
    }, 500)
  }

  // eslint-disable-next-line camelcase
  Plugin.prototype.calculate = function (single_column_mode) {
    var self = this
    // eslint-disable-next-line no-unused-vars
    var tallest = 0
    var row = 0
    var $container = $(this.element)
    // eslint-disable-next-line camelcase,no-unused-vars
    var container_width = $container.width()
    $article = $(this.element).children()

    // eslint-disable-next-line camelcase
    if (single_column_mode === true) {
      // eslint-disable-next-line camelcase
      article_width = $container.width() - self.options.padding_x
    } else {
      // eslint-disable-next-line camelcase
      article_width = ($container.width() - self.options.padding_x * self.options.no_columns) / self.options.no_columns
    }

    $article.each(function () {
      $(this).css('width', article_width)
    })

    columns = self.options.no_columns

    $article.each(function (index) {
      // eslint-disable-next-line camelcase
      var current_column
      // eslint-disable-next-line camelcase
      var left_out = 0
      var top = 0
      var $this = $(this)
      var prevAll = $this.prevAll()
      // eslint-disable-next-line no-unused-vars
      var tallest = 0

      // eslint-disable-next-line camelcase
      if (single_column_mode === false) {
        // eslint-disable-next-line camelcase
        current_column = (index % columns)
      } else {
        // eslint-disable-next-line camelcase
        current_column = 0
      }

      for (var t = 0; t < columns; t++) {
        $this.removeClass('c' + t)
      }

      if (index % columns === 0) {
        row++
      }

      // eslint-disable-next-line camelcase
      $this.addClass('c' + current_column)
      $this.addClass('r' + row)

      prevAll.each(function (index) {
        // eslint-disable-next-line camelcase
        if ($(this).hasClass('c' + current_column)) {
          top += $(this).outerHeight() + self.options.padding_y
        }
      })

      // eslint-disable-next-line camelcase
      if (single_column_mode === true) {
        // eslint-disable-next-line camelcase
        left_out = 0
      } else {
        // eslint-disable-next-line camelcase
        left_out = (index % columns) * (article_width + self.options.padding_x)
      }

      $this.css({
        'left': left_out,
        'top': top
      })
    })

    this.tallest($container)
    $(window).resize()
  }

  Plugin.prototype.tallest = function (_container) {
    // eslint-disable-next-line camelcase
    var column_heights = []
    var largest = 0

    for (var z = 0; z < columns; z++) {
      // eslint-disable-next-line camelcase
      var temp_height = 0
      _container.find('.c' + z).each(function () {
        // eslint-disable-next-line camelcase
        temp_height += $(this).outerHeight()
      })
      // eslint-disable-next-line camelcase
      column_heights[z] = temp_height
    }

    largest = Math.max.apply(Math, column_heights)
    _container.css('height', largest + (this.options.padding_y + this.options.margin_bottom))
  }

  Plugin.prototype.make_layout_change = function (_self) {
    if ($(window).width() < _self.options.single_column_breakpoint) {
      _self.calculate(true)
    } else {
      _self.calculate(false)
    }
  }

  $.fn[pluginName] = function (options) {
    return this.each(function () {
      if (!$.data(this, 'plugin_' + pluginName)) {
        $.data(this, 'plugin_' + pluginName,
          new Plugin(this, options))
      }
    })
  }
})(jQuery, window, document)

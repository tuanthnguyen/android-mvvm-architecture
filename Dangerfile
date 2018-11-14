return unless status_report[:errors].length.zero? && status_report[:warnings].length.zero?
message("LGTM :+1:\nWaiting for your review!\n@imtuann")

android_lint.lint

 # ktLint
github.dismiss_out_of_range_messages
checkstyle_format.base_path = Dir.pwd
checkstyle_format.report 'app/build/reports/ktlint/ktlint-checkstyle-report.xml'

 # AndroidLint
android_lint.report_file = "app/build/reports/lint-results.xml"
android_lint.skip_gradle_task = true
android_lint.severity = "Error"
android_lint.lint(inline_mode: true)
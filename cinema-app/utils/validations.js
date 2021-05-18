export const change_password = {
  old_password: [
    "old_password",
    {
      rules: [
        { required: true, message: "Dữ liệu này là bắt buộc." },
        { whitespace: true, message: "Không được có ký tự trống ở đầu hoặc cuối input" },
      ]
    }
  ],
  password: [
    "password",
    {
      rules: [
        { required: true, message: "Dữ liệu này là bắt buộc." },
        { whitespace: true, message: "Không được có ký tự trống ở đầu hoặc cuối input" },
      ]
    }
  ],
  password_confirmation: [
    "password_confirmation",
    {
      rules: [
        { required: true, message: "Dữ liệu này là bắt buộc." },
        { whitespace: true, message: "Không được có ký tự trống ở đầu hoặc cuối input" },
      ]
    }
  ],
}